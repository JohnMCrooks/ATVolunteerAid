package com.skoorc.atvolunteeraid.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.skoorc.atvolunteeraid.database.LocationDatabase
import com.skoorc.atvolunteeraid.model.LocationRepository
import com.skoorc.atvolunteeraid.model.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#10
class LocationViewModel(application: Application): AndroidViewModel(application) {
    private val repository: LocationRepository
    //   Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allLocations: LiveData<List<Location>>
    val totalLocationCount: LiveData<Int>

    init {
        val locationDAO = LocationDatabase.getDatabase(
            application,
            viewModelScope
        ).locationDAO()
        repository =
            LocationRepository(locationDAO)
        allLocations = repository.allLocations
        totalLocationCount = repository.totalLocationCount
    }

    fun insert(location: Location) = viewModelScope.launch(Dispatchers.IO) {
        Log.i("LocationViewModel", "insert Location: ${location}")
        repository.insert(location)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        Log.i("LocationViewModel", "deleteAll")
        repository.deleteAll()
    }

    fun deleteById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteById(id)
        TODO("implement this eventually")
    }
}