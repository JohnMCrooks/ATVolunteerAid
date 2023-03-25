package com.skoorc.atvolunteeraid.view.components.issueList

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.skoorc.atvolunteeraid.ui.theme.ATVolunteerAidTheme
import com.skoorc.atvolunteeraid.util.MockUtil

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun IssueListComposeFragment() {
    val issueList = MockUtil().getListOfLocations(20)
    Surface {
        Box() {
            Column() {
                //Header (Total # of reports & filter button)
                IssueListHeader()
                //Scrollable List of Issues
                IssueListScrollableArea(issueList)
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview
@Composable
fun IssueListComposeFragmentPreview() {
    ATVolunteerAidTheme {
        IssueListComposeFragment()
    }
}