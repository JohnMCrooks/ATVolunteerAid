package com.skoorc.atvolunteeraid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#10
@Database(entities = arrayOf(Location::class), version = 1, exportSchema = false)
abstract class LocationDatabase: RoomDatabase() {
    abstract fun locationDAO(): LocationDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: LocationDatabase? = null

        fun getDatabase(context: Context): LocationDatabase{
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocationDatabase::class.java,
                    "location_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}