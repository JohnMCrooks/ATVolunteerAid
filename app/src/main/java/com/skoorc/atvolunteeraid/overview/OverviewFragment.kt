package com.skoorc.atvolunteeraid.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.skoorc.atvolunteeraid.R
import com.skoorc.atvolunteeraid.util.showSnackbar
import kotlinx.android.synthetic.main.fragment_overview.view.*

class OverviewFragment: Fragment() {
    private val TAG = "fragmentOverview"
    private lateinit var layout: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG,   "Entering OnCreateView for overview fragment")
        return  inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG,   "Entering OnCreate for overview fragment")
        Log.i(TAG,   "Leaving OnCreate for overview fragment")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG,   "Entering OnViewCreated for overview fragment")

        view.markLocationButton.setOnClickListener {
            Log.i(TAG, "OnViewCreated Click Location has been clicked")
            onClickReportLocation(it)
            Snackbar.make(it, "Report button clicked", Snackbar.LENGTH_SHORT).show()
        }
        view.mapButton.setOnClickListener {
            Log.i(TAG, "OnViewCreated Click Map button has been clicked")
            Snackbar.make(it, "Map button clicked", Snackbar.LENGTH_LONG).show()
        }
        Log.i(TAG,   "Leaving OnViewCreated for overview fragment")
    }

    fun onClickReportLocation(view: View) {
        Log.i(TAG,   "Inside onClickReportLocation")
        Toast.makeText(context, "It's a Doodie", Toast.LENGTH_SHORT).show()
    }

}