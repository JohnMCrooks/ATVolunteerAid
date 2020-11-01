package com.skoorc.atvolunteeraid

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.skoorc.atvolunteeraid.util.checkSelfPermissionCompat
import com.skoorc.atvolunteeraid.util.requestPermissionsCompat
import com.skoorc.atvolunteeraid.util.shouldShowRequestPermissionRationaleCompat
import com.skoorc.atvolunteeraid.util.showSnackbar

const val PERMISSION_REQUEST_FINE_LOCATION = 0
const val PERMISSION_REQUEST_COARSE_LOCATION = 1
const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {
    private lateinit var layout: View
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var latitudeText: TextView
    private lateinit var longitudeText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "Pre-view inflation")
        setContentView(R.layout.activity_main)
        layout = findViewById(R.id.overview_fragment)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkGPSPermissions()
        Log.i(TAG, "End of onCreate created")

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "start onRequestPermissionsResult")
        if (requestCode == PERMISSION_REQUEST_FINE_LOCATION) {
            // Request for camera permission.
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission was granted for ACCESS_FINE_LOCATION ")
                // Permission has been granted. Do whatever with the GPS info.
//                TODO("Record location and log it probably using GPS location service")
                getLastLocation()
            } else {
                // Permission request was denied.)
                Log.i(TAG, "Permission was Denied for ACCESS_FINE_LOCATION ")
            }
        }
    }

    private fun checkGPSPermissions() {
        Log.i(TAG, "start checkGPSPermissions ")
        // Check if the GPS permission has been granted
        if (checkSelfPermissionCompat(Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            Log.i(TAG, "GPS Permissions already exist")
            getLastLocation()
        } else {
            Log.i(TAG, "GPS Permissions don't exist yet - requesting permissions")
            // Permission is missing and must be requested.
            requestGPSPermission()
        }
    }

    /**
     * Requests the [android.Manifest.permission.ACCESS_FINE_LOCATION] permission.
     * If an additional rationale should be displayed, the user has to launch the request from
     * a SnackBar that includes additional information.
     */
    private fun requestGPSPermission() {
        Log.i(TAG, "Start GPS permissions request")
        // Permission has not been granted and must be requested.
        if (shouldShowRequestPermissionRationaleCompat(Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            layout.showSnackbar("GPS access required for the app to work",
                Snackbar.LENGTH_INDEFINITE, "OK") {
                requestPermissionsCompat(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_FINE_LOCATION)
            }

        } else {
            Log.i(TAG, "GPS Access Denied, requesting permission")
            // Request the permission. The result will be received in onRequestPermissionResult().
            requestPermissionsCompat(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_FINE_LOCATION)
        }
    }

    fun getLastLocation() {
        Log.i(TAG, "inside getLastLocation")
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i(TAG, "Either Fine or Coarse were denied, Location won't be found")
            return
        } else {
            Log.i(TAG, "All necessary permissions granted proceeding to get new location")
//            TODO("Figure out why Locations are coming back with a null value on the emualtor.")
            fusedLocationClient.lastLocation
                .addOnCompleteListener { taskLocation ->
                    Log.i(TAG, "inside location listener")
                    if (taskLocation.isSuccessful) {
                        val location = taskLocation.result
                        latitudeText = findViewById(R.id.LatValue)
                        longitudeText = findViewById(R.id.longValue)
                        latitudeText.text = location?.latitude.toString()
                        Log.i(TAG, "Lat: ${location.latitude}")
                        Log.i(TAG, "Long: ${location.longitude}")
                        longitudeText.text = location?.longitude.toString()
                    } else {
                        Log.i(TAG, "No location detected")
                    }
                }
        }
    }
}