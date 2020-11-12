package com.skoorc.atvolunteeraid.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.skoorc.atvolunteeraid.model.Location
import com.skoorc.atvolunteeraid.model.LocationDAO
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
                "35.467338",
                "-82.572414",
                "11/1/2020",
                "Trash"
            )
            locationDao.insertLocation(locationPlaceholder)
            var locationPlaceholder2 = Location(
                "39.060910",
                "-76.517500",
                "11/2/2020",
                "Incorrect blaze"
            )
            locationDao.insertLocation(locationPlaceholder2)
            locationPlaceholder2.date = "11/3/2020"
            locationPlaceholder2.latitude = "39.050310"
            locationPlaceholder2.type = "Trail Damage"
            locationPlaceholder = Location(
                "35.407338",
                "-82.5072414",
                "11/4/2020",
                "Bad Blaze"
            )
            locationDao.insertLocation(locationPlaceholder)
            locationPlaceholder = Location(
                "35.497338",
                "-82.772414",
                "11/5/2020",
                "Poop"
            )
            locationDao.insertLocation(locationPlaceholder)
            locationPlaceholder = Location(
                "35.50",
                "-82.70",
                "11/6/2020",
                "Tree Blocking Trail"
            )
            locationDao.insertLocation(locationPlaceholder)
            locationPlaceholder = Location(
                "35.52",
                "-82.72",
                "11/7/2020",
                "More Poop"
            )
            locationDao.insertLocation(locationPlaceholder)
            locationDao.insertLocation(locationPlaceholder2)
        }
    }
}