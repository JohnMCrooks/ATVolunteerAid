package com.skoorc.atvolunteeraid.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#10
@Entity(tableName = "location_table")
 data class Location(
    @PrimaryKey(autoGenerate=true) @ColumnInfo(name="id") var id: Int,
                @ColumnInfo (name="latitude") var latitude: String,
                @ColumnInfo (name="longitude") var longitude: String,
                @ColumnInfo (name="date_added") var date: String) {

}