package com.skoorc.atvolunteeraid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDAO {

    @Query ("SELECT * FROM location_table ORDER BY date_added DESC")
    fun getAllLocations(): LiveData<List<Location>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocation(location: Location)

    @Query("DELETE from location_table")
    suspend fun deleteAll()

    @Query ("DELETE from location_table where id = :id")
    fun deleteById(id: Int)
}