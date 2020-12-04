package com.skoorc.atvolunteeraid.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skoorc.atvolunteeraid.R
import com.skoorc.atvolunteeraid.viewmodel.LocationListAdapter
import com.skoorc.atvolunteeraid.viewmodel.LocationViewModel
import com.skoorc.atvolunteeraid.viewmodel.LocationViewModelFactory
import kotlinx.android.synthetic.main.fragment_list_view.view.*

//Recycler view references here
//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#12
class ListFragment: Fragment() {
    val TAG = "LocationListFragment"
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var viewModelFactory: LocationViewModelFactory

    //TODO: Add Long press to delete recycler view items from List and DB
    //TODO: Tap on individual item from list to open map on that pin marker.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_view, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = LocationListAdapter(view.context)
        val locationCount = view.findViewById<TextView>(R.id.listCountTotalTextView)
        val context = getContext()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        locationViewModel = context?.let { LocationViewModelFactory(it).create(LocationViewModel::class.java) }!!

        // Observers to update data from DB as changes are made (addition/deletion of locations)
        locationViewModel.allLocations.observe(viewLifecycleOwner, Observer { locations ->
            locations.let { adapter.setLocations(it) }
        })
        locationViewModel.totalLocationCount.observe(viewLifecycleOwner, Observer {totalCount ->
            locationCount.text = totalCount.toString()
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.fab.setOnClickListener {
            locationViewModel.deleteAll()
        }
    }
}