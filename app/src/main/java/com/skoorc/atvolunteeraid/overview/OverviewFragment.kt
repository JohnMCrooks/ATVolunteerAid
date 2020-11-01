package com.skoorc.atvolunteeraid.overview

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.skoorc.atvolunteeraid.R
import kotlinx.android.synthetic.main.fragment_overview.view.*

class OverviewFragment: Fragment() {
    private val TAG = "fragmentOverview"
    private lateinit var layout: View
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var latitudeText: TextView
    private lateinit var longitudeText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG,   "Entering OnCreateView for overview fragment")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext() as Context)
        return   inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG,   "Entering OnCreate for overview fragment")
        Log.i(TAG,   "Leaving OnCreate for overview fragment")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout = view
        Log.i(TAG,   "Entering OnViewCreated for overview fragment")

        view.markLocationButton.setOnClickListener {
            Log.i(TAG, "OnViewCreated Click Location has been clicked")
            onClickReportLocation(it)
            Snackbar.make(it, "Report button clicked", Snackbar.LENGTH_SHORT).show()
        }
        view.mapButton.setOnClickListener {
            Log.i(TAG, "OnViewCreated Click Map button has been clicked")
            Snackbar.make(it, "Map button clicked", Snackbar.LENGTH_LONG).show()
        }
        Log.i(TAG,   "Leaving OnViewCreated for overview fragment")
    }

    fun onClickReportLocation(view: View) {
        Log.i(TAG,   "Inside onClickReportLocation")
        Toast.makeText(context, "It's a Doodie", Toast.LENGTH_SHORT).show()
        getLastLocation()
    }

    fun getLastLocation() {
        Log.i(com.skoorc.atvolunteeraid.TAG, "inside getLastLocation")
        if (ActivityCompat.checkSelfPermission(
                getContext() as Context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext() as Context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i(com.skoorc.atvolunteeraid.TAG, "Either Fine or Coarse were denied, Location won't be found")
            return
        } else {
            Log.i(com.skoorc.atvolunteeraid.TAG, "All necessary permissions granted proceeding to get new location")
            fusedLocationClient.lastLocation
                .addOnCompleteListener { taskLocation ->
                    Log.i(com.skoorc.atvolunteeraid.TAG, "inside location listener")
                    if (taskLocation.isSuccessful) {
                        val location = taskLocation.result
                        var listTextView = layout.tempTextView
                        Log.i(TAG, "Random num to show it's running: ${Math.random()}")
                        Log.i(com.skoorc.atvolunteeraid.TAG, "Lat: ${location.latitude}")
                        Log.i(com.skoorc.atvolunteeraid.TAG, "Long: ${location.longitude}")
                        val textPlaceholder =  "${listTextView.text} \n ${location.latitude}, ${location.longitude}"
                         listTextView.text = textPlaceholder
                    } else {
                        Log.i(com.skoorc.atvolunteeraid.TAG, "No location detected")
                    }
                }
        }
    }
}