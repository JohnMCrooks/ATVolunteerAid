package com.skoorc.atvolunteeraid.overview

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.skoorc.atvolunteeraid.R
import com.skoorc.atvolunteeraid.database.LocationViewModel
import kotlinx.android.synthetic.main.fragment_overview.view.*
import java.text.DateFormat
import java.util.*

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
            Log.i(TAG, "Mark location clicked")
            onClickReportLocation(it)
        }
        view.listButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_overviewFragment_to_ListFragment)
        }
        view.mapButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_overviewFragment_to_mapsActivity)
        }
    }

    fun onClickReportLocation(view: View) {
//        getLastLocation_OG()
        logLastLocationSlim()
        Toast.makeText(context, "It's a Doodie", Toast.LENGTH_SHORT).show()
    }

    fun logLastLocationSlim() {
        Log.d(TAG, "Entering LogLastLocationSlim")
        //TODO when report button is clicked, inflate fragment w/ problems for user to click on to report (trail blocked, trash, poop, Bad/missing Markers)

// **** Testing the DB DAO/insert functionality since button is working (generates toast) and data is being retrieved from DB w/o issue  *****
        if (ActivityCompat.checkSelfPermission(
                getContext() as Context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext() as Context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "Don't have the correct permissions")
        } else {
            Log.i(TAG, "Permissions already exist")
            //This won't work well on an emulator, use a real device to verify
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.i(TAG, "Success in scope: ${it.result.longitude}, ${it.result.latitude}")
                        val newLocation = com.skoorc.atvolunteeraid.database.Location(it.result.latitude.toString(), it.result.longitude.toString(),  getDateString(),
                            type = "trash"
                        )
                        Log.d(TAG, "New Location: $newLocation")
                        locationViewModel.insert(newLocation)
                        Log.i(TAG, "Location Added")
                    } else {
                        Log.i(TAG, "Location Listener failed or returned null")
                    }
                }
        }
    }


// ***********************************
// THIS IS  WORKING DO NOT DELETE!!!
// ***********************************
//    fun getLastLocation_OG() {
//        if (ActivityCompat.checkSelfPermission(
//                getContext() as Context,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                getContext() as Context,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        } else {
//            Log.i(TAG, "All necessary permissions granted proceeding to get new location")
//            fusedLocationClient.lastLocation
//                .addOnCompleteListener { taskLocation ->
//                    Log.i(TAG, "inside location listener")
//                    if (taskLocation.isSuccessful) {
//                        val location = taskLocation.result
//                        var listTextView = layout.tempTextView
//                        Log.i(TAG, "Random num to show it's running: ${Math.random()}")
//                        Log.i(TAG, "Lat: ${location.latitude}")
//                        Log.i(TAG, "Long: ${location.longitude}")
//                        val textPlaceholder =  "${listTextView.text} \n ${location.latitude}, ${location.longitude}"
//                         listTextView.text = textPlaceholder
//                    } else {
//                        Log.i(TAG, "No location detected")
//                    }
//                }
//        }
//    }
    fun getDateString(): String {
        val date: Date = Calendar.getInstance().time
        Log.i("Date", "Date string format: ${date.toString()}")
        val dateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val strDate = dateFormat.format(date)
        Log.i("Date", "Date string format: ${strDate}")
        return strDate
    }
}