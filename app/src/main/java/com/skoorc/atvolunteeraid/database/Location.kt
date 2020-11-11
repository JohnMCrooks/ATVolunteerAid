package com.skoorc.atvolunteeraid.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#10
@Entity(tableName = "location_table")
 data class Location(
                @ColumnInfo (name = "latitude") var latitude: String,
                @ColumnInfo (name = "longitude") var longitude: String,
                @ColumnInfo (name = "date_added") var date: String,
                @ColumnInfo (name = "type") var type: String){
    @PrimaryKey(autoGenerate = true) var id: Int = 0

    override fun toString(): String {
        return "Lat: $latitude, long: $longitude, date: $date, id: $id"
    }
}