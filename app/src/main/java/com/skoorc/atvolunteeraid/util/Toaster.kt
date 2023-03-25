package com.skoorc.atvolunteeraid.util

import android.content.Context
import android.widget.Toast

class Toaster {
    fun showToast(context: Context, message: String, length: Int) {
        when (length) {
            0 -> Toast.makeText( context, message, Toast.LENGTH_SHORT).show()
            1 -> Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            else -> Toast.makeText(context, "something went horribly wrong... ", Toast.LENGTH_SHORT).show()
        }
    }

}