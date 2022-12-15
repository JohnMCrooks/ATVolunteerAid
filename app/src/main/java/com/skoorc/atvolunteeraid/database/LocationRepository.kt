package com.skoorc.atvolunteeraid.database

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.asFlow
import com.skoorc.atvolunteeraid.model.Location
import kotlinx.coroutines.flow.Flow
import java.util.*

//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#10
class LocationRepository (private val locationDAO: LocationDAO) {

    val allLocations: Flow<List<Location>> = locationDAO.getAllLocations().asFlow()
    val resolvedLocations: Flow<List<Location>> = locationDAO.getResolvedLocations().asFlow()
    val unresolvedLocations: Flow<List<Location>> = locationDAO.getUnresolvedLocations().asFlow()
    val totalLocationCount: Flow<Int> = locationDAO.getLocationCount().asFlow()
    val resolvedLocationCount: Flow<Int> = locationDAO.getResolvedLocationCount().asFlow()
    val unresolvedLocationCount: Flow<Int> = locationDAO.getUnresolvedLocationCount().asFlow()

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

    @WorkerThread
    fun setIssueResolved(id: Int) {
        locationDAO.markIssueResolved(id)
    }

    fun getLocationByUUID(uuid: UUID): Flow<Location> {
        return locationDAO.getLocationByUUID(uuid).asFlow()
    }
}