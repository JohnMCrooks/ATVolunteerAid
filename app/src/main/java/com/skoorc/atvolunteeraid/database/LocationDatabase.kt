package com.skoorc.atvolunteeraid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// DB reference --> https://developer.android.com/codelabs/android-room-with-a-view-kotlin#10
@Database(entities = arrayOf(Location::class), version = 1, exportSchema = false)
abstract class LocationDatabase: RoomDatabase() {
    abstract fun locationDAO(): LocationDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: LocationDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): LocationDatabase{
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocationDatabase::class.java,
                    "location_database"
                ).addCallback(LocationDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

//    Database callback reference
//    https://developer.android.com/codelabs/android-room-with-a-view-kotlin#12
    private class LocationDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch{
                    prePopulateDatabase(database.locationDAO())
                }

            }
        }
        suspend fun prePopulateDatabase(locationDao: LocationDAO) {
            locationDao.deleteAll()
            var locationPlaceholder = Location(1, "35.467338", "-82.572414", "11/1/2020")
            locationDao.insertLocation(locationPlaceholder)
            var locationPlaceholder2 = Location(2, "39.060910", "-76.517500", "11/2/2020")
            locationDao.insertLocation(locationPlaceholder2)
            locationPlaceholder2.id = 3
            locationPlaceholder2.date = "11/1/2020"
            locationPlaceholder2.latitude = "39.050310"
            locationDao.insertLocation(locationPlaceholder2)
        }
    }
}
