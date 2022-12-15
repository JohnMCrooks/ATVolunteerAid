package com.skoorc.atvolunteeraid.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.skoorc.atvolunteeraid.model.Location
import com.skoorc.atvolunteeraid.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// DB reference --> https://developer.android.com/codelabs/android-room-with-a-view-kotlin#10
@Database(entities = [Location::class, User::class], version = 5, exportSchema = false)
abstract class LocationDatabase: RoomDatabase() {
    abstract fun locationDAO(): LocationDAO
    val TAG = "LocationDatabase"

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
        val TAG = "LocationDatabaseCallback"
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
                "Trash",
                "Unresolved",
                "JC"
            )
            locationDao.insertLocation(locationPlaceholder)
            locationPlaceholder = Location(
                "37.6751536590001",
                "-79.334662259",
                "10/12/2021",
                "Trash",
                "Resolved",
                "Mary",
                resolvedBy = "Joseph",
                dateResolved = "11/20/2021"
            )
            locationDao.insertLocation(locationPlaceholder)
            locationPlaceholder = Location(
                "37.6751566590001",
                "-79.334662259",
                "10/13/2021",
                "Trash",
                "Resolved",
                "Joseph",
                resolvedBy = "JC",
                dateResolved = "11/20/2021"
            )
            locationDao.insertLocation(locationPlaceholder)
            var locationPlaceholder2 = Location(
                "43.5371713170001",
                "-72.871504692",
                "2/11/2021",
                "Incorrect blaze",
                "Unresolved",
                "JC"
            )
            locationDao.insertLocation(locationPlaceholder2)
            locationPlaceholder2.date = "3/11/2021"
            locationPlaceholder2.longitude = "-72.8622028029999"
            locationPlaceholder2.latitude = "43.5401500850001"
            locationPlaceholder2.type = "Trail Damage"
            locationPlaceholder2.status = "Unresolved"
            locationPlaceholder2.reportedBy = "JMC"

            locationPlaceholder = Location(
                "43.537097008",
                "-72.871430381",
                "4/11/2021",
                "Incorrect Blaze",
                "Sarah",
                "Unresolved"
            )
            locationDao.insertLocation(locationPlaceholder)
            locationPlaceholder = Location(
                "37.96466664",
                "-78.887264834",
                "5/10/2021",
                "Trash",
                "Mikey",
                "Unresolved"
            )
            locationDao.insertLocation(locationPlaceholder)
            locationPlaceholder = Location(
                "37.964727768",
                "-78.877290035",
                "6/09/2021",
                "Trail Blocked",
                "Sarah",
                "Unresolved"
            )
            locationDao.insertLocation(locationPlaceholder)
            locationPlaceholder = Location(
                "37.960556593",
                "-78.892579674",
                "6/09/2021",
                "Trail Blocked",
                "JC",
                "Resolved",
                resolvedBy = "Mary",
                dateResolved = "12/01/2021"
            )
            locationDao.insertLocation(locationPlaceholder)
            Log.d(TAG, locationPlaceholder.toString())
            locationDao.insertLocation(locationPlaceholder2)
        }
    }
}