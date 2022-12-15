package com.skoorc.atvolunteeraid.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skoorc.atvolunteeraid.R
import com.skoorc.atvolunteeraid.model.Location
import com.skoorc.atvolunteeraid.viewmodel.LocationListAdapter
import com.skoorc.atvolunteeraid.viewmodel.LocationViewModel
import com.skoorc.atvolunteeraid.viewmodel.LocationViewModelFactory
import kotlinx.android.synthetic.main.fragment_list_view.view.*

//Recycler view references here
//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#12
class ListFragment: Fragment(), LocationListAdapter.OnItemClickListener, LocationListAdapter.OnItemLongClickListener {
    val TAG = "LocationListFragment"
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var adapter: LocationListAdapter
    private lateinit var observedList: LiveData<List<Location>>
    private lateinit var observedCount: LiveData<Int>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "Entering OnCreateView")
        activity?.setTitle(R.string.title_activity_report_list)
        val view = inflater.inflate(R.layout.fragment_list_view, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        adapter = LocationListAdapter(view.context, this)
        val locationCount = view.findViewById<TextView>(R.id.listCountTotalTextView)
        val context = context

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        locationViewModel = context?.let { LocationViewModelFactory(it).create(LocationViewModel::class.java) }!!
        // initialize the list to be observed
        setListObservers(view, true)

        // Observers to update data from DB as changes are made (addition/deletion of locations)
        view.button_filter.setOnClickListener { v ->
            setListObservers(view, false)
        }
        return view
    }

    //rotates through the "filters" on button click
    private fun setListObservers(view: View, initialLoad: Boolean) {
        if (initialLoad) {
            observedList = locationViewModel.allLocations
            observedCount = locationViewModel.totalLocationCount
            setCountTextAndFilterText(
                view,
                "Total # of Reports",
                "All")
        } else {
            when(view.button_filter.text) {
                "All", "Filter" -> {
                    Log.d(TAG, "updating filters to unresolved locations")
                    observedList = locationViewModel.allUnresolvedLocations
                    observedCount = locationViewModel.unresolvedLocationCount
                    setCountTextAndFilterText(
                        view,
                        "# of Unresolved",
                        "Filter - Unresolved")
                }
                "Filter - Unresolved" -> {
                    Log.d(TAG, "updating filters to Resolved locations")
                    observedList = locationViewModel.allResolvedLocations
                    observedCount = locationViewModel.resolvedLocationCount
                    setCountTextAndFilterText(
                        view,
                        "# of Resolved",
                        "Filter - Resolved")
                }
                else -> {
                    Log.d(TAG, "updating filters to All locations")
                    observedList = locationViewModel.allLocations
                    observedCount = locationViewModel.totalLocationCount
                    setCountTextAndFilterText(
                        view,
                        "Total # of Reports",
                        "All")
                }
            }
        }
        observedList.observe(viewLifecycleOwner, Observer { locations ->
            locations.let { adapter.setLocations(it) }
        })
        observedCount.observe(viewLifecycleOwner, Observer { count ->
            view.listCountTotalTextView.text = count.toString()
        })
    }

    private fun setCountTextAndFilterText(view: View, countLabelText: String, filterText: String) {
        view.button_filter.text = filterText
        view.listCountLabel.text = countLabelText
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.fab.setOnClickListener {
            locationViewModel.deleteAll()
        }
    }

    /**
     * Will attempt to start the map activity on click.
     */
    override fun onItemClick(recyclerPosition: Int, id: Int, location: String) {
        //TODO Look for a more refined way of
        // doing this than passing extras in through the intent
        Toast.makeText(context, "Let's go to the MAP!", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Entering OnItemClick - location: $location")
        startMapIntent(id, location)
    }

    private fun startMapIntent(id: Int, location: String) {
        val intent = Intent(requireContext(), MapsActivity::class.java)
        intent.putExtra("LOCATION_ID", id)
        intent.putExtra("LOCATION_LAT_LONG", location)
        startActivity(intent)
    }
    /**
     * Will pop open a dialog and give the user a chance to resolve the issue, go to the map or hit dismiss the dialog.
     */
    fun onItemLongClick(recyclerPosition: Int, idValue: Int, latLng: String) {
        //TODO Make this pop up a verification that the item has been fixed/removed/Cleaned up
        //TODO Schema update should include resolved true/false. remove deletion (keep all entries for analytics eventually)
        Log.d(TAG,"Recycler item long clicked, position: $recyclerPosition, ID: $idValue")

        val copyLocationToClipboardButtonClick =  { _: DialogInterface, which: Int ->
            Log.d(TAG, "dialog interface num: $which - Copy lat/long button")
            locationViewModel.copyLatLongToClipboard(requireContext(), latLng)
            Toast.makeText(context, "Lat/Long Copied! \n $latLng", Toast.LENGTH_SHORT).show()
            startMapIntent(id, latLng)
        }
        val seeItOnTheMapButtonClick = { dialog: DialogInterface, which: Int ->
            Log.d(TAG, "dialog interface num: $which - See it on the map button")
            Toast.makeText(context, "Let's go to the Map!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()

            startMapIntent(id, latLng)
        }

        val resolvedButtonClick = { dialogue: DialogInterface, which: Int ->
            Log.d(TAG, "dialog interface num: $which - Issue Resolved button")
            locationViewModel.markResolved(idValue)
            dialogue.dismiss()
        }

        val cancelButtonClick = { dialogue:DialogInterface, which: Int ->
            Log.d(TAG, "dialog interface num: $which - Cancel button")
            dialogue.cancel()
        }

        with(AlertDialog.Builder(context)) {
            setTitle("Title")
            setItems(
                arrayOf<CharSequence>("Copy Coords", "Mark Resolved", "Go to Map", "Cancel")
            ) { dialog, which ->
                when (which) {
                    0 -> copyLocationToClipboardButtonClick.invoke(dialog, which)
                    1 -> resolvedButtonClick.invoke(dialog, which)
                    2 -> seeItOnTheMapButtonClick.invoke(dialog, which)
                    3 -> cancelButtonClick.invoke(dialog, which)
                    else -> dialog.cancel()
                }
            }.create().show()
        }
    }

    override fun onItemLongClick(recyclerPosition: Int, idValue: Int) {
        TODO("Not yet implemented")
    }
}