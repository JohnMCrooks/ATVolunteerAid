package com.skoorc.atvolunteeraid.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#10
@Entity(tableName = "location_table")
 data class Location(
                @ColumnInfo (name = "latitude") var latitude: String,
                @ColumnInfo (name = "longitude") var longitude: String,
                @ColumnInfo (name = "date_added") var date: String,
                @ColumnInfo (name = "type") var type: String,
                @ColumnInfo (name = "status") var status: String,
                @ColumnInfo (name = "reported_by") var reportedBy: String,
                @ColumnInfo (name = "uuid") var locationUUID: String = UUID.randomUUID().toString(),
                @ColumnInfo (name = "additional_info") var additionalInfo: String? = null,
                @ColumnInfo (name = "resolved_by") var resolvedBy: String? = null,
                @ColumnInfo (name = "date_resolved") var dateResolved: String? = null) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0

    override fun toString(): String {
        return "id: $id" +
                "\nstatus:$status " +
                "\ntype: $type, reported by:$reportedBy" +
                "\ndate: $date,  " +
                "\nLat: $latitude, long: $longitude" +
                "\nadditional info:$additionalInfo" +
                "\nresolvedBy: $resolvedBy, dateResolve:$dateResolved" +
                "\nUUID: $locationUUID"
    }
}