package com.skoorc.atvolunteeraid.database

import androidx.lifecycle.LiveData

//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#10
class LocationRepository (private val locationDAO: LocationDAO) {

    val allLocations: LiveData<List<Location>> = locationDAO.getAllLocations()

    suspend fun insert(location: Location) {
        locationDAO.insertLocation(location)
    }

    suspend fun deleteAll() {
        locationDAO.deleteAll()
    }

    fun deleteById(id: Int) {
        locationDAO.deleteById(id)
    }

}