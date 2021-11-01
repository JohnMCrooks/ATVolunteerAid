package com.skoorc.atvolunteeraid.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.skoorc.atvolunteeraid.database.LocationDatabase
import com.skoorc.atvolunteeraid.database.LocationRepository
import com.skoorc.atvolunteeraid.model.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#10
class LocationViewModel(context: Context): ViewModel() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { LocationDatabase.getDatabase(context, applicationScope) }
    private val locationRepo by lazy {
        LocationRepository(
            database.locationDAO()
        )
    }
    //   Using LiveData and caching what's returned has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allLocations: LiveData<List<Location>> = locationRepo.allLocations.asLiveData()
    val totalLocationCount: LiveData<Int> = locationRepo.totalLocationCount.asLiveData()
//    val getLatestLocation: LiveData<Location> = locationRepo.getLatestLocation.asLiveData()

    fun insert(location: Location) = viewModelScope.launch(Dispatchers.IO) {
        Log.i("LocationViewModel", "insert Location: $location")
        locationRepo.insert(location)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        Log.i("LocationViewModel", "deleteAll")
        locationRepo.deleteAll()
    }

    fun deleteById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        Log.i("LocationViewModel", "deleteByID $id")
        locationRepo.deleteById(id)
    }
}

class LocationViewModelFactory( val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return LocationViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}