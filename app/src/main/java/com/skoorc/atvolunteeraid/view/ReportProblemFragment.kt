package com.skoorc.atvolunteeraid.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.skoorc.atvolunteeraid.R
import com.skoorc.atvolunteeraid.util.LocationUtil
import com.skoorc.atvolunteeraid.util.showToast

class ReportProblemFragment: Fragment() {
    private val TAG = "fragment_reportProblems"
    // TODO Add onResume and OnPause overrides to maintain context so app doesn't crash when
    // coming back from background on All fragments/activities

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG,   "Entering Report Problem fragment")
        return inflater.inflate(R.layout.fragment_problem_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionReportToOverview = R.id.action_reportProblemFragment_to_overviewFragment
        val reportedPoopToast = getString(R.string.report_poop_button_toast)
        val reportTrailBlockageToast = getString(R.string.report_trail_blocked_button_toast)
        val reportTrashToast = getString(R.string.report_trash_button_toast)
        val reportBadTrailMarker = getString(R.string.report_bad_trail_marker_button_toast)

        view.findViewById<Button>(R.id.button_report_blocked_trail).setOnClickListener() {
            LocationUtil().logLastLocationSlim(getString(R.string.report_trail_blocked_button_label), context as Context)
            view.findNavController().navigate(actionReportToOverview)
            view.showToast(reportTrailBlockageToast, LENGTH_SHORT)
        }
        view.findViewById<Button>(R.id.button_report_poop).setOnClickListener() {
            LocationUtil().logLastLocationSlim("Poop", context as Context)
            view.findNavController().navigate(actionReportToOverview)
            view.showToast(reportedPoopToast, LENGTH_SHORT)
        }
        view.findViewById<Button>(R.id.button_report_trail_marker).setOnClickListener() {
            LocationUtil().logLastLocationSlim("Bad Trail Marker", context as Context)
            view.findNavController().navigate(actionReportToOverview)
            view.showToast(reportBadTrailMarker, LENGTH_SHORT)
        }
        view.findViewById<Button>(R.id.button_report_trash).setOnClickListener() {
            LocationUtil().logLastLocationSlim("Trash", context as Context)
            view.findNavController().navigate(actionReportToOverview)
            view.showToast(reportTrashToast, LENGTH_SHORT)
        }
    }
}