package com.skoorc.atvolunteeraid.database

import android.util.Log
import androidx.annotation.WorkerThread
import com.skoorc.atvolunteeraid.model.User

class UserRepository(private val userDAO: UserDAO) {
    private val TAG = "UserRepository"

    @WorkerThread
    suspend fun insertUser(user: User) {
        Log.i(TAG, "Inserting new user ${user.nickname}, admin status:${user.adminStatus}, emaail:${user.email}")
        userDAO.insertUser(user);
    }

    @WorkerThread
    suspend fun deleteUserById(id: Int) {
        Log.i(TAG, "Attempting to delete user id: $id")
        userDAO.deleteUserById(id);
    }

    @WorkerThread
    suspend fun deleteAllUsers() {
        Log.i(TAG, "Attempting to delete all users")
        userDAO.deleteAllUsers();
    }

}