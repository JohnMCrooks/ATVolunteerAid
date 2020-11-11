package com.skoorc.atvolunteeraid.overview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.observe

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.skoorc.atvolunteeraid.R
import com.skoorc.atvolunteeraid.database.LocationViewModel

internal class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var locationViewModel: LocationViewModel

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

        locationViewModel = LocationViewModel(application)
        mMap = googleMap
        val locationList = locationViewModel.allLocations
        var firstlatLong: LatLng? = null
        locationList.observe( this, Observer { it ->
            it.forEach {
                Log.i("Maps", "$it")
                var latLong: LatLng = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                    if (firstlatLong == null) {
                        firstlatLong = latLong
                        Log.i("Maps", "First LatLng = ${firstlatLong}")
                    }
                val title: String = "${it.type} - ${it.date}"
                mMap.addMarker(MarkerOptions().position(latLong).title(title))
            }
         })
        // Add a marker in Sydney and move the camera
        val eastCoastUSA = LatLng(39.05, -82.0)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eastCoastUSA))
    }
}