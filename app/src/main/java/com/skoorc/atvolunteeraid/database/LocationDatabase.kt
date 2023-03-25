package com.skoorc.atvolunteeraid.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.skoorc.atvolunteeraid.model.Location
import com.skoorc.atvolunteeraid.model.User
import com.skoorc.atvolunteeraid.util.MockUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// DB reference --> https://developer.android.com/codelabs/android-room-with-a-view-kotlin#10
@Database(entities = [Location::class, User::class], version = 5, exportSchema = false)
abstract class LocationDatabase: RoomDatabase() {
    abstract fun locationDAO(): LocationDAO
    abstract fun userDAO(): UserDAO
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
        val mockUtil = MockUtil()
        val TAG = "LocationDatabaseCallback"
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch{
                    prePopulateDatabase(database.locationDAO(), database.userDAO())
                }
            }
        }
        suspend fun prePopulateDatabase(locationDao: LocationDAO, userDao: UserDAO) {
            Log.i("DatabaseInit", "pre-populating database")
            locationDao.deleteAll()

            // Staging Mock Reports
            val mockLocationList: List<Location> = MockUtil().getListOfLocations(20)
            mockLocationList.forEach {
                locationDao.insertLocation(it)
            }

            // Staging Mock Users
            for (i in 0..5) {
                userDao.insertUser(mockUtil.getRandomUser())
            }
        }
    }
}