package com.skoorc.atvolunteeraid.viewmodel

import android.content.ClipData
import android.content.ClipboardManager
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
    val TAG = "LocationViewModel"
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
    val allResolvedLocations: LiveData<List<Location>> = locationRepo.resolvedLocations.asLiveData()
    val allUnresolvedLocations: LiveData<List<Location>> = locationRepo.unresolvedLocations.asLiveData()
    val totalLocationCount: LiveData<Int> = locationRepo.totalLocationCount.asLiveData()
    val resolvedLocationCount: LiveData<Int> = locationRepo.resolvedLocationCount.asLiveData()
    val unresolvedLocationCount: LiveData<Int> = locationRepo.unresolvedLocationCount.asLiveData()

    fun insert(location: Location) = viewModelScope.launch(Dispatchers.IO) {
        Log.i(TAG, "insert Location: $location")
        locationRepo.insert(location)
    }

    fun markResolved(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        Log.i(TAG, "Mark Issue/Location Resolved. ID # $id")
        locationRepo.setIssueResolved(id)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        Log.i(TAG, "deleteAll")
        locationRepo.deleteAll()
    }

    fun deleteById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        Log.i(TAG, "deleteByID $id")
        locationRepo.deleteById(id)
    }

    fun getLocationById(id: Int): Location {
        Log.d(TAG, "Getting location by ID: $id")
        return locationRepo.getLocationByID(id)
    }

    var myClipboard: ClipboardManager? = null
    var myClip: ClipData? = null


    fun copyLatLongToClipboard(context: Context, latLng: String) {
        val location  = latLng.replace("\n", " ")

        Log.d(TAG, "location: $location -  latLng: $latLng")
        myClipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?

        myClip = ClipData.newPlainText("text", latLng);
        myClipboard?.setPrimaryClip(myClip as ClipData);
    }

    fun getClipboardText(): String {
        val clipData = myClipboard?.primaryClip
        val item = clipData?.getItemAt(0)

        return item?.text.toString()
    }
}

class LocationViewModelFactory( val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return LocationViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}