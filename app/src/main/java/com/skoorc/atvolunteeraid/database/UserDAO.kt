package com.skoorc.atvolunteeraid.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skoorc.atvolunteeraid.model.Location
import com.skoorc.atvolunteeraid.model.User

@Dao
interface UserDAO {

    // Core CRUD SQL Queries
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Query("DELETE from user_table")
    suspend fun deleteAllUsers()

    @Query("DELETE from user_table where id = :id")
    fun deleteUserById(id: Int)

    @Query ("SELECT * FROM user_table ORDER BY date_joined DESC")
    fun getAllUsers(): LiveData<List<User>>

    // To be used if and when I setup an admin utility wayyyyyy down the road.
    @Query ("SELECT * FROM user_table WHERE admin_status='true' ORDER BY date_joined DESC")
    fun getAllAdminUsers(): LiveData<List<User>>

    @Query ("UPDATE user_table SET admin_status=:boolean WHERE id= :userId")
    fun setAdminStatusById(userId: Int, boolean: Boolean)

    @Query ("UPDATE user_table SET is_shadow_banned=:boolean WHERE id= :userId")
    fun setShadowBanStatusById(userId: Int, boolean: Boolean)

}
