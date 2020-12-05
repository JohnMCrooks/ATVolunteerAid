package com.skoorc.atvolunteeraid.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.data.kml.KmlLayer
import com.skoorc.atvolunteeraid.R
import com.skoorc.atvolunteeraid.viewmodel.LocationViewModel
import com.skoorc.atvolunteeraid.viewmodel.LocationViewModelFactory
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

internal class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val TAG = "MapsActivity"
    private lateinit var mMap: GoogleMap
    private val eastCoastUSA = LatLng(38.814512, -77.159754)

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
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val locationViewModel = LocationViewModelFactory(baseContext).create(LocationViewModel::class.java)
        val locationList = locationViewModel.allLocations

        //TODO find a way to parse this programmatically rather than manually writing it out as an array
        val atFileArray = arrayOf(R.raw.at_break_1, R.raw.at_break_2, R.raw.at_break_3,
            R.raw.at_break_4, R.raw.at_break_5, R.raw.at_break_6, R.raw.at_break_7, R.raw.at_break_8,
            R.raw.at_break_9, R.raw.at_break_10)
        loadAtLayers(atFileArray)

        val initialPinID =  intent?.getIntExtra("LOCATION_ID", -1)
        val initialPinLatLngString: List<String>? = intent?.getStringExtra("LOCATION_LAT_LONG")?.split(",")
        val initialPinLatLng: LatLng
        initialPinLatLng = if (!initialPinLatLngString.isNullOrEmpty()) {
            LatLng(
                initialPinLatLngString[0].toDouble(),
                initialPinLatLngString[1].toDouble()
            )
        } else {
            eastCoastUSA
        }
        Log.i(TAG, "testing PinID passed w/ intent $initialPinID")
        Log.i(TAG, "testing Converted LatLong $initialPinLatLng")

        when(initialPinLatLng) {
            eastCoastUSA -> mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPinLatLng, 5f))
            else -> {
                Log.i(TAG, "Came from List view, zooming in on specified Pin")
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPinLatLng, 10f))
            }
        }

        locationList.observe( this, Observer { it ->
            it.forEach {
                Log.i(TAG, "$it")
                var latLong = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                val title = "#${it.id}: ${it.type} - ${it.date}"
                val iconBitMapDescriptor = getIconType(it.type.toLowerCase(Locale.ROOT))
                mMap.addMarker(MarkerOptions().position(latLong).title(title).icon(iconBitMapDescriptor))
            }
         })
    }

    private fun loadAtLayers(at_file_array: Array<Int>) {
        at_file_array.forEach {
            doAsync {
                val kml_segment = KmlLayer(mMap, it, baseContext)
                uiThread {
                    drawAtLayer(kml_segment)
                }
            }
        }
    }

    private fun drawAtLayer(kmlLayer: KmlLayer) {
        kmlLayer.addLayerToMap()
    }

    private fun getIconType(problemType: String): BitmapDescriptor {
        return when(problemType) {
            "poop" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)
            "trail blocked" ->  BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            "bad trail marker" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)
            "trash" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
            else -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)
        }
    }
}