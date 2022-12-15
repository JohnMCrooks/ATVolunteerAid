package com.skoorc.atvolunteeraid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skoorc.atvolunteeraid.model.Location
import java.util.*

//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#10
@Dao
interface LocationDAO {

    @Query ("SELECT * FROM location_table ORDER BY date_added DESC")
    fun getAllLocations(): LiveData<List<Location>>

    @Query ("SELECT * FROM location_table WHERE status='Resolved' ORDER BY date_added DESC")
    fun getResolvedLocations(): LiveData<List<Location>>

    @Query ("SELECT * FROM location_table WHERE status='Unresolved' ORDER BY date_added DESC")
    fun getUnresolvedLocations(): LiveData<List<Location>>

    @Query ("SELECT COUNT(*) FROM location_table")
    fun getLocationCount(): LiveData<Int>

    @Query ("SELECT COUNT(*) FROM location_table WHERE status='Resolved'")
    fun getResolvedLocationCount(): LiveData<Int>

    @Query ("SELECT COUNT(*) FROM location_table WHERE status='Unresolved'")
    fun getUnresolvedLocationCount(): LiveData<Int>

    @Query ("SELECT * FROM location_table WHERE uuid=:uuid")
    fun getLocationByUUID(uuid: String): LiveData<Location>

    @Query ("SELECT * FROM location_table WHERE id=:id")
    fun getLocationByID(id: Int): Location

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocation(location: Location)

    @Query("DELETE from location_table")
    suspend fun deleteAll()

    @Query ("DELETE from location_table where id = :id")
    fun deleteById(id: Int)

    @Query ("UPDATE location_table set status='Resolved' WHERE id= :id")
    fun markIssueResolved(id: Int)
}