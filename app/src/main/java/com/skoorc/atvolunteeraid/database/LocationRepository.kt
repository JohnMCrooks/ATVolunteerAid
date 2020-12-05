package com.skoorc.atvolunteeraid.database

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.asFlow
import com.skoorc.atvolunteeraid.model.Location
import kotlinx.coroutines.flow.Flow

//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#10
class LocationRepository (private val locationDAO: LocationDAO) {

    val allLocations: Flow<List<Location>> = locationDAO.getAllLocations().asFlow()
    val totalLocationCount: Flow<Int> = locationDAO.getLocationCount().asFlow()
    val getLatestLocation = locationDAO.getLatestLocation().asFlow()

    @WorkerThread
    suspend fun insert(location: Location) {
        Log.i("LocationRepository", "pre Location Repo insert")
        locationDAO.insertLocation(location)
    }

    @WorkerThread
    suspend fun deleteAll() {
        locationDAO.deleteAll()
        Log.i("LocationRepository", "All Location Entries Deleted")
    }

    @WorkerThread
    fun deleteById(id: Int) {
        locationDAO.deleteById(id)
        Log.i("LocationRepository", "Location entry Deleted by ID $id")
    }
}