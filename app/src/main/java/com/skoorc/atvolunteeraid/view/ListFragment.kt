package com.skoorc.atvolunteeraid.view

import android.app.AlertDialog
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
                    observedList = locationViewModel.allUnresolvedLocations
                    observedCount = locationViewModel.unresolvedLocationCount
                    setCountTextAndFilterText(
                        view,
                        "# of Unresolved",
                        "Filter - Unresolved")
                }
                "Filter - Unresolved" -> {
                    observedList = locationViewModel.allResolvedLocations
                    observedCount = locationViewModel.resolvedLocationCount
                    setCountTextAndFilterText(
                        view,
                        "# of Resolved",
                        "Filter - Resolved")
                }
                else -> {
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

    override fun onItemClick(recyclerPosition: Int, id: Int, location: String) {
        //TODO Look for a more refined way of
        // doing this than passing extras in through the intent
        Toast.makeText(context, "Let's go to the MAP!", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireContext(), MapsActivity::class.java)
        intent.putExtra("LOCATION_ID", id)
        intent.putExtra("LOCATION_LAT_LONG", location)
        startActivity(intent)
    }

    override fun onItemLongClick(recyclerPosition: Int, idValue: Int) {
        //TODO Make this pop up a verification that the item has been fixed/removed/Cleaned up
        //TODO Schema update should include resolved true/false. remove deletion (keep all entries for analytics eventually)
        Log.d(TAG,"Recycler item long clicked, position: $recyclerPosition, ID: $idValue")
        val dialogueBuilder = AlertDialog.Builder(context)
        dialogueBuilder.setMessage("Has the issue been cleaned up or fixed??")
            .setCancelable(false)
            .setPositiveButton("Yup, Issue resolved") { dialogue, _ ->
                locationViewModel.markResolved(idValue)
                dialogue.dismiss()
            }
            .setNegativeButton("Cancel") { dialogue, _ ->
                dialogue.cancel()
            }
        val alert = dialogueBuilder.create()
        alert.setTitle("Issue resolution")
        alert.show()
    }
}