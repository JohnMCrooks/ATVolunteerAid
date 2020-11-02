package com.skoorc.atvolunteeraid.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LocationViewModel(application: Application): AndroidViewModel(application) {
    private val repository: LocationRepository
    //   Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allLocations: LiveData<List<Location>>

    init {
        val locationDAO = LocationDatabase.getDatabase(application).locationDAO()
        repository = LocationRepository(locationDAO)
        allLocations = repository.allLocations
    }

    fun insert(location: Location) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(location)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun deleteById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteById(id)
    }
}