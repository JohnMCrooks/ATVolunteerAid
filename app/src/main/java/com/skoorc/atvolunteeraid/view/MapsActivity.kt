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
import com.skoorc.atvolunteeraid.util.LocationUtil
import com.skoorc.atvolunteeraid.viewmodel.LocationViewModel
import com.skoorc.atvolunteeraid.viewmodel.LocationViewModelFactory
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

//TODO Split this activity into proper view/viewModel to follow MVVM guidelines
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
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
        val atFileArray = getAtResourceIds()
        if (atFileArray.isNotEmpty())  {
            loadLocalKMLFiles(atFileArray)
        } else {
            throw error("Failed to parse At File Array somewhere in the chain")
        }

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
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPinLatLng, 10f))
            }
        }

        locationList.observe( this, Observer { it ->
            it.forEach {
//                Log.i(TAG, "$it")
                var latLong = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                val title = "#${it.id}: ${it.type} - ${it.date}"
                val iconBitMapDescriptor = getIconType(it.type.toLowerCase(Locale.ROOT))
                mMap.addMarker(MarkerOptions().position(latLong).title(title).icon(iconBitMapDescriptor))
            }
        })
    }

    private fun loadLocalKMLFiles(at_file_array: List<Int>) {
        var kmlArray: MutableList<KmlLayer> = mutableListOf<KmlLayer>()
        var layerLoadCounter = 0

        Log.i(TAG, "AtArraySize: ${at_file_array.size}")
        at_file_array.forEach {
            layerLoadCounter += 1
            doAsync {
                val kmlLayer = KmlLayer(mMap, it, baseContext)
                uiThread {
                    drawAtLayer(kmlLayer)
                }
            }
        }
//---------------------------------------------------------------------------------------
//            doAsync {
//                KmlLayer(mMap, it, baseContext).doAsyncResult {
//                    uiThread {
//                        drawAtLayer(it)
//                    }
//                }
//            }
//---------------------------------------------------------------------------------------
//            GlobalScope.launch {
//                kmlLayer = KmlLayer(mMap, it, baseContext)
//            }
//            runOnUiThread {
//                drawAtLayer(kmlLayer)
//            }
//
//---------------------------------------------------------------------------------------
        Log.i(TAG, "layerLoader final count: $layerLoadCounter")
    }

    private fun drawAtLayer(kmlLayer: KmlLayer) {
        kmlLayer.addLayerToMap()
    }

    private fun getIconType(problemType: String): BitmapDescriptor {
        return when(problemType) {
            "poop" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)
            "trail blocked" ->  BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            "bad trail marker" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
            "trash" -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
            else -> BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
        }
    }

    private fun getAtResourceIds(): List<Int> {
        val atResourceStringList = LocationUtil().getATResourceStringList()
        Log.i(TAG, "Resource String list size: ${atResourceStringList.size}")
        val resourceIdArray = mutableListOf<Int>()
        for (i in atResourceStringList.indices) {
            val idString = atResourceStringList[i]
            resourceIdArray.add(i, getResourceIdFromString(idString))
        }
        Log.i(TAG, "resourceIDArray Count: ${resourceIdArray.size}")
        return resourceIdArray
    }
    private fun getResourceIdFromString(resourceString: String): Int {
        return this.resources.getIdentifier(resourceString, "raw", this.packageName)
    }
}