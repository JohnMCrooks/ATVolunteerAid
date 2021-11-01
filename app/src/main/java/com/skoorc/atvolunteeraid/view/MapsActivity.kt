package com.skoorc.atvolunteeraid.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.data.kml.KmlLayer
import com.skoorc.atvolunteeraid.R
import com.skoorc.atvolunteeraid.viewmodel.LocationViewModel
import com.skoorc.atvolunteeraid.viewmodel.LocationViewModelFactory
import com.skoorc.atvolunteeraid.viewmodel.MapViewModel
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.*

//TODO Split this activity into proper view/viewModel to follow MVVM guidelines
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val TAG = "MapsActivity"
    private lateinit var mMap: GoogleMap
    private val eastCoastUSA = LatLng(38.814512, -77.159754)
    private val mapViewModel = MapViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        loadingSpinner.visibility = View.VISIBLE
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onMapReady(googleMap: GoogleMap) {
        val locationViewModel = LocationViewModelFactory(baseContext).create(LocationViewModel::class.java)
        val locationList = locationViewModel.allLocations
        mMap = googleMap
        enableMyLocationAndZoomButtons(mMap)
        setInitialCenterAndZoom()
        var kmlList: MutableList<KmlLayer> = mapViewModel.loadAtResources(mMap)
        Log.i(TAG, "kmlList final count: ${kmlList.size}")
        kmlList.forEach { kmlLayer ->
            drawAtLayer(kmlLayer)
        }

        locationList.observe( this, Observer { it ->
            it.forEach {
                var latLong = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                val title = "#${it.id}: ${it.type} - ${it.date}"
                val iconBitMapDescriptor = getIconType(it.type.toLowerCase(Locale.ROOT))
                mMap.addMarker(MarkerOptions().position(latLong).title(title).icon(iconBitMapDescriptor))
            }
        })
        loadingSpinner.visibility = View.INVISIBLE
    }


//  TODO: Revisit this later to find a better/more efficient way of loading the AT trail guidelines
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

    private fun enableMyLocationAndZoomButtons(mMap: GoogleMap) {
        //show my location button + zoom controls
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
    }

    private fun setInitialCenterAndZoom() {
        var initialPinLatLng = getInitialMapLocation()
        when(initialPinLatLng) {
            eastCoastUSA -> mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPinLatLng, 5f))
            else -> {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPinLatLng, 10f))
            }
        }
    }

    /*
     * If the map is entered from the main screen, we show the overview of the entire AT trail.
     * If the map is entered from the Issue list, it will pass a lat long of the item the user clicked to use as a starting center location
     */
    private fun getInitialMapLocation(): LatLng {
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
        return initialPinLatLng
    }
}