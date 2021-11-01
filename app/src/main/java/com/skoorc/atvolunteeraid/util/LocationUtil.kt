package com.skoorc.atvolunteeraid.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.skoorc.atvolunteeraid.model.Location
import com.skoorc.atvolunteeraid.viewmodel.LocationViewModel
import com.skoorc.atvolunteeraid.viewmodel.LocationViewModelFactory
import java.util.*


class LocationUtil {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationViewModel: LocationViewModel

    fun logLastLocationSlim(problemType: String, context: Context) {
        Log.d(TAG, "Entering LogLastLocationSlim")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        locationViewModel = LocationViewModelFactory(context).create(LocationViewModel::class.java)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
               context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e(TAG, "Don't have the correct permissions")
        } else {
            Log.d(TAG, "Permissions already exist")
            //This won't work well on an emulator, use a real device to verify
            fusedLocationClient.lastLocation
                .addOnFailureListener {
                    Log.e(TAG, "Failed to Obtain location")
                }
                .addOnSuccessListener {
                    if (it != null) {
                        Log.i(TAG, "Success in scope: ${it.longitude}, ${it.latitude}")
                        val newLocation =
                            Location(
                                it.latitude.toString(),
                                it.longitude.toString(),
                                getDateString(),
                                problemType
                            )
                        Log.d(TAG, "New Location: $newLocation")
                        locationViewModel.insert(newLocation)
                        Log.i(TAG, "Location Added")
                    } else {
                        //for testing on an emulator
                        val newLocation =
                            Location(
                                "37.7913",
                                "-122.4789",
                                getDateString(),
                                "Emulator Failure"
                            )
                        Log.d(TAG, "New Location b.c. emulator sucks: $newLocation")
                        locationViewModel.insert(newLocation)
                        Log.i(TAG, "Location Added")
                    }
                }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateString(): String {
        val date: Date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val strDate = dateFormat.format(date)
        return strDate
    }

    fun getATResourceStringList(): List<String> {
        var resourceIdArray = mutableListOf<String>()
        for(i in 1..60) {
            val name = "at_break_$i"
            Log.i("LocationUtil", "List Entry: $i, $name")
            resourceIdArray.add(i-1, name)
        }
        return resourceIdArray.toList()
    }
}