package com.skoorc.atvolunteeraid.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.data.kml.KmlLayer
import com.skoorc.atvolunteeraid.util.LocationUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ConcurrentLinkedQueue

class MapViewModel(private val context: Context): ViewModel() {
    private val TAG = "MapViewModel"
    private var kmlLayerList: MutableList<KmlLayer> = mutableListOf()

    /*
     *  Gather file resource Ids, pass them to method to load into KMLLayer objects
     */
    fun loadAtResources(googleMap: GoogleMap): MutableList<KmlLayer> {
        val atFileArray = getAtResourceIds()
        if (atFileArray.isNotEmpty())  {
            return loadLocalKMLFiles(atFileArray, googleMap)
        } else {
            throw error("Failed to parse At File Array somewhere in the chain")
        }
    }

    private fun getAtResourceIds(): List<Int> {
        val atResourceStringList = LocationUtil().getATResourceStringList()
        Log.v(TAG, "Resource String list size: ${atResourceStringList.size}")
        val resourceIdArray = mutableListOf<Int>()
        for (i in atResourceStringList.indices) {
            val idString = atResourceStringList[i]
            resourceIdArray.add(i, getResourceIdFromString(idString))
        }
        Log.v(TAG, "resourceIDArray Count: ${resourceIdArray.size}")
        return resourceIdArray
    }

    private fun getResourceIdFromString(resourceString: String): Int {
        return context.resources.getIdentifier(resourceString, "raw", context.packageName)
    }

    private fun loadLocalKMLFiles(at_file_array: List<Int>, googleMap: GoogleMap): MutableList<KmlLayer>  {
        Log.v(TAG, "AtArraySize: ${at_file_array.size}")
//        at_file_array.forEach {
//            layerLoadCounter += 1
//            doAsync {
//                val kmlLayer = KmlLayer(googleMap, it, context)
//                kmlLayerList.add(kmlLayer)
//            }
//        }
        var linkedQueue = ConcurrentLinkedQueue<KmlLayer>()
        runBlocking {
            at_file_array.forEach {resourceId ->
                launch(Dispatchers.IO) {
                    val kmlLayer = KmlLayer(googleMap, resourceId, context)
                    linkedQueue.add(kmlLayer)
                }
            }
        }
        kmlLayerList = linkedQueue.toMutableList()
        Log.v(TAG, "kmlLayerList size post load: ${kmlLayerList.size}")
        return kmlLayerList
    }
}