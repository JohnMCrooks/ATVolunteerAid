package com.skoorc.atvolunteeraid.model

import android.util.Log
import androidx.lifecycle.LiveData

//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#10
class LocationRepository (private val locationDAO: LocationDAO) {

    val allLocations: LiveData<List<Location>> = locationDAO.getAllLocations()
    val totalLocationCount: LiveData<Int> = locationDAO.getLocationCount()

    suspend fun insert(location: Location) {
        Log.i("LocationRepository", "pre Location Repo insert")
        locationDAO.insertLocation(location)
    }

    suspend fun deleteAll() {
        locationDAO.deleteAll()
        Log.i("LocationRepository", "All Location Entries Deleted")
    }

    fun deleteById(id: Int) {
        locationDAO.deleteById(id)
    }
}