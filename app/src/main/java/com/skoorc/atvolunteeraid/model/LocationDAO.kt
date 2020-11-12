package com.skoorc.atvolunteeraid.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skoorc.atvolunteeraid.model.Location

//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#10
@Dao
interface LocationDAO {

    @Query ("SELECT * FROM location_table ORDER BY date_added DESC")
    fun getAllLocations(): LiveData<List<Location>>

    @Query ("SELECT COUNT(*) FROM location_table")
    fun getLocationCount(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocation(location: Location)

    @Query("DELETE from location_table")
    suspend fun deleteAll()

    @Query ("DELETE from location_table where id = :id")
    fun deleteById(id: Int)
}