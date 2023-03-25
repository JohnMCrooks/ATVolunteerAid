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

            var locationPlaceholder = Location(
                "37.6751636590001",
                "-79.334662259",
                mockUtil.randomDate(),
                mockUtil.randomIssueType(),
                "Unresolved",
                mockUtil.randomName()
            )
            locationDao.insertLocation(locationPlaceholder)
            locationPlaceholder = Location(
                "37.6751536590001",
                "-79.334662259",
                mockUtil.randomDate(),
                mockUtil.randomIssueType(),
                "Resolved",
                mockUtil.randomName(),
                resolvedBy = mockUtil.randomName(),
                dateResolved = mockUtil.randomDate()
            )
            locationDao.insertLocation(locationPlaceholder)
            locationPlaceholder = Location(
                "37.6751566590001",
                "-79.334662259",
                mockUtil.randomDate(),
                mockUtil.randomIssueType(),
                "Resolved",
                mockUtil.randomName(),
                resolvedBy = mockUtil.randomName(),
                dateResolved = "11/20/2021"
            )
            locationDao.insertLocation(locationPlaceholder)
            val locationPlaceholder2 = Location(
                "43.5371713170001",
                "-72.871504692",
                mockUtil.randomDate(),
                mockUtil.randomIssueType(),
                "Unresolved",
                mockUtil.randomName()
            )
            locationDao.insertLocation(locationPlaceholder2)
            locationPlaceholder2.date = mockUtil.randomDate()
            locationPlaceholder2.longitude = "-72.8622028029999"
            locationPlaceholder2.latitude = "43.5401500850001"
            locationPlaceholder2.type = mockUtil.randomIssueType()
            locationPlaceholder2.status = "Unresolved"
            locationPlaceholder2.reportedBy = mockUtil.randomName()

            locationPlaceholder = Location(
                "43.537097008",
                "-72.871430381",
                mockUtil.randomDate(),
                mockUtil.randomIssueType(),
                "Unresolved",
                mockUtil. randomName()
            )
            locationDao.insertLocation(locationPlaceholder)
            locationPlaceholder = Location(
                "37.96466664",
                "-78.887264834",
                mockUtil.randomDate(),
                mockUtil.randomIssueType(),
                "Unresolved",
                mockUtil.randomName()

            )
            locationDao.insertLocation(locationPlaceholder)
            locationPlaceholder = Location(
                "37.964727768",
                "-78.877290035",
                mockUtil.randomDate(),
                mockUtil.randomIssueType(),
                "Unresolved",
                mockUtil.randomName()
            )
            locationDao.insertLocation(locationPlaceholder)
            locationPlaceholder = Location(
                "37.960556593",
                "-78.892579674",
                mockUtil.randomDate(),
                mockUtil.randomIssueType(),
                "Resolved",
                mockUtil.randomName(),
                resolvedBy = mockUtil.randomName(),
                dateResolved = "12/01/2021"
            )
            locationDao.insertLocation(locationPlaceholder)
            Log.d(TAG, locationPlaceholder.toString())
            locationDao.insertLocation(locationPlaceholder2)


            fun newUser(): User {
                return User(
                    mockUtil.randomEmail(),
                    mockUtil.randomName(),
                    mockUtil.randomDate(),
                    false,
                    mockUtil.randomState(),
                    false
                )
            }
            userDao.insertUser(newUser())
            userDao.insertUser(newUser())
            userDao.insertUser(newUser())
            userDao.insertUser(newUser())
        }
    }
}