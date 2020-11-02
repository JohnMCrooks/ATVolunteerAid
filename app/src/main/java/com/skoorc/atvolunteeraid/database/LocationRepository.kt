package com.skoorc.atvolunteeraid.database

import androidx.lifecycle.LiveData

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