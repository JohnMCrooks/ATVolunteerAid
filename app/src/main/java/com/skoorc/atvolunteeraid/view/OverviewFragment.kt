package com.skoorc.atvolunteeraid.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.skoorc.atvolunteeraid.R
import com.skoorc.atvolunteeraid.viewmodel.LocationViewModel
import kotlinx.android.synthetic.main.fragment_overview.view.*

class OverviewFragment: Fragment() {
    private val TAG = "fragmentOverview"
    private lateinit var layout: View
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationViewModel: LocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG,   "Entering OnCreateView for overview fragment")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext() as Context)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout = view

        view.markLocationButton.setOnClickListener {
            Log.i(TAG, "Report issue clicked")
            view.findNavController().navigate(R.id.action_overviewFragment_to_reportProblemFragment)
        }
        view.listButton.setOnClickListener {
            Log.i(TAG, "view list clicked")
            view.findNavController().navigate(R.id.action_overviewFragment_to_ListFragment)
        }
        view.mapButton.setOnClickListener {
            Log.i(TAG, "Map button clicked")
            view.findNavController().navigate(R.id.action_overviewFragment_to_mapsActivity)
        }
    }
}