package com.skoorc.atvolunteeraid.overview

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationServices
import com.skoorc.atvolunteeraid.R
import com.skoorc.atvolunteeraid.database.LocationListAdapter
import com.skoorc.atvolunteeraid.database.LocationViewModel

//Recycler view references here
//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#12
class ListFragment: Fragment() {
    val TAG = "LocationListFragment"
    private lateinit var layout: View
    private lateinit var locationViewModel: LocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_view, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = LocationListAdapter(view.context)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        locationViewModel.allLocations.observe(viewLifecycleOwner, Observer { locations ->
            locations.let { adapter.setLocations(it) }
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}