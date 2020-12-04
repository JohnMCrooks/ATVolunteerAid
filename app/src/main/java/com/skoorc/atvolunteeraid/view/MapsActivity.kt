package com.skoorc.atvolunteeraid.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.skoorc.atvolunteeraid.R
import com.skoorc.atvolunteeraid.model.Location
import com.skoorc.atvolunteeraid.viewmodel.LocationViewModel
import com.skoorc.atvolunteeraid.viewmodel.LocationViewModelFactory

internal class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val TAG = "Maps"
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val locationViewModel = LocationViewModelFactory(baseContext).create(LocationViewModel::class.java)
        val locationList = locationViewModel.allLocations
        val newestLocation: LiveData<Location> = locationViewModel.getLatestLocation
        val initialPinID =  intent?.getIntExtra("LOCATION_ID", -1)
        val initialPinLatLngString = intent?.getStringExtra("LOCATION_LAT_LONG")?.split(",")
        val initialPinLatLng: LatLng? = LatLng(initialPinLatLngString?.get(0)?.toDouble()!!,
            initialPinLatLngString[1].toDouble())

        Log.i(TAG, "testing PinID passed w/ intent $initialPinID")
        Log.i(TAG, "testing Converted LatLong $initialPinLatLng")

        locationList.observe( this, Observer { it ->
            it.forEach {
                Log.i(TAG, "$it")
                var latLong: LatLng = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                val title: String = "${it.type} - ${it.date}"
                mMap.addMarker(MarkerOptions().position(latLong).title(title))
            }
         })
        newestLocation.observe( this, Observer { it ->
            when(initialPinLatLng) {
                null -> {
                    if (it != null) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude.toDouble(), it.longitude.toDouble()), 12f))
                    } else {
                        val eastCoastUSA = LatLng(39.05, -82.0)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eastCoastUSA, 5f))
                    }
                }
                else -> {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPinLatLng, 12f))
                }
            }
        })
    }
}