package com.skoorc.atvolunteeraid.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skoorc.atvolunteeraid.R
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_overview.view.*

class OverviewFragment: Fragment() {
    private val TAG = "fragmentOverview"
    private lateinit var layout: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG,   "Entering OnCreateView for overview fragment")
        activity?.setTitle(R.string.app_name)
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout = view

        layout.markLocationButton.setOnClickListener {
            Log.i(TAG, "Report issue clicked")
            layout.findNavController().navigate(R.id.action_overviewFragment_to_reportProblemFragment)
        }
        layout.listButton.setOnClickListener {
            Log.i(TAG, "view list clicked")
            layout.findNavController().navigate(R.id.action_overviewFragment_to_ListFragment)
        }
        layout.mapButton.setOnClickListener {
            Log.i(TAG, "Map button clicked")
            layout.findNavController().navigate(R.id.action_overviewFragment_to_mapsActivity)
        }
    }
}