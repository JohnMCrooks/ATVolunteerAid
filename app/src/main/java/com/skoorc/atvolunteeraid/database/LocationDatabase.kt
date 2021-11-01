package com.skoorc.atvolunteeraid.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.skoorc.atvolunteeraid.model.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// DB reference --> https://developer.android.com/codelabs/android-room-with-a-view-kotlin#10
@Database(entities = arrayOf(Location::class), version = 2, exportSchema = false)
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
                ).addCallback(LocationDatabaseCallback(scope)).fallbackToDestructiveMigration().build()
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
            Log.i("DatabaseInit", "pre-populating database")
            locationDao.deleteAll()
            var locationPlaceholder = Location(
                "37.6751636590001",
                "-79.334662259",
                "1/11/2021",
                "Trash"
            )
            locationDao.insertLocation(locationPlaceholder)
            var locationPlaceholder2 = Location(
                "43.5371713170001",
                "-72.871504692",
                "2/11/2021",
                "Incorrect blaze"
            )
            locationDao.insertLocation(locationPlaceholder2)
            locationPlaceholder2.date = "3/11/2021"
            locationPlaceholder2.longitude = "-72.8622028029999"
            locationPlaceholder2.latitude = "43.5401500850001"
            locationPlaceholder2.type = "Trail Damage"
            locationPlaceholder = Location(
                "43.537097008",
                "-72.871430381",
                "4/11/2021",
                "Incorrect Blaze"
            )
            locationDao.insertLocation(locationPlaceholder)
            locationPlaceholder = Location(
                "37.96466664",
                "-78.887264834",
                "5/10/2021",
                "Trash"
            )
            locationDao.insertLocation(locationPlaceholder)
            locationPlaceholder = Location(
                "37.964727768",
                "-78.877290035",
                "6/09/2021",
                "Trail Blocked"
            )
            locationDao.insertLocation(locationPlaceholder)
            locationPlaceholder = Location(
                "37.960556593",
                "-78.892579674",
                "6/09/2021",
                "Trail Blocked"
            )
            locationDao.insertLocation(locationPlaceholder)
            locationDao.insertLocation(locationPlaceholder2)
        }
    }
}