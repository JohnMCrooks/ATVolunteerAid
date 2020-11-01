package com.skoorc.atvolunteeraid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "Pre-view inflation")
        setContentView(R.layout.activity_main)
        Log.i(TAG, "End of onCreate created")
    }
}