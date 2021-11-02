package com.skoorc.atvolunteeraid.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @ColumnInfo (name = "email") var email: String,
    @ColumnInfo (name = "nickname") var nickname: String,
    @ColumnInfo (name = "date_joined") var dateJoined: String,
    @ColumnInfo (name = "admin_status") var adminStatus: Boolean,
    @ColumnInfo (name = "state_of_residence") var state: String,
    @ColumnInfo (name = "is_shadow_banned") var isShadowBanned: Boolean
    ) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}