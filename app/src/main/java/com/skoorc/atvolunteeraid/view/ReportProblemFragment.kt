package com.skoorc.atvolunteeraid.view

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
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.skoorc.atvolunteeraid.R
import com.skoorc.atvolunteeraid.viewmodel.LocationViewModel
import com.skoorc.atvolunteeraid.model.Location
import java.util.*

class ReportProblemFragment: Fragment() {
    private val TAG = "fragment_reportProblems"
    private lateinit var layout: View
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationViewModel: LocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG,   "Entering Report Problem fragment")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context as Context)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        return inflater.inflate(R.layout.fragment_problem_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_report_blocked_trail).setOnClickListener() {
            logLastLocationSlim("Trail Blocked")
            view.findNavController().navigate(R.id.action_reportProblemFragment_to_overviewFragment)
        }
        view.findViewById<Button>(R.id.button_report_poop).setOnClickListener() {
            logLastLocationSlim("Poop")
            view.findNavController().navigate(R.id.action_reportProblemFragment_to_overviewFragment)
        }
        view.findViewById<Button>(R.id.button_report_trail_marker).setOnClickListener() {
            logLastLocationSlim("Bad Trail Marker")
            view.findNavController().navigate(R.id.action_reportProblemFragment_to_overviewFragment)
        }
        view.findViewById<Button>(R.id.button_report_trash).setOnClickListener() {
            logLastLocationSlim("Trash")
            view.findNavController().navigate(R.id.action_reportProblemFragment_to_overviewFragment)
        }
    }

    fun logLastLocationSlim(problemType: String) {
        Log.d(TAG, "Entering LogLastLocationSlim")

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
                        val newLocation =
                            Location(
                                it.result.latitude.toString(),
                                it.result.longitude.toString(),
                                getDateString(),
                                problemType
                            )
                        Log.d(TAG, "New Location: $newLocation")
                        locationViewModel.insert(newLocation)
                        Log.i(TAG, "Location Added")
                        Toast.makeText(context, "Problem Reported, Thanks!", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.i(TAG, "Location Listener failed or returned null")
                    }
                }
        }
    }

    fun getDateString(): String {
        val date: Date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val strDate = dateFormat.format(date)
        return strDate
    }
}