package com.skoorc.atvolunteeraid.view.components.problemReport

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.skoorc.atvolunteeraid.R
import com.skoorc.atvolunteeraid.ui.theme.ATVolunteerAidTheme

@Composable
fun ReportProblemFragment() {
    Surface {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            ProblemCard(reportType = "Incorrect/Damaged trail marker", associatedImage = R.drawable.trail_blaze_icon)
            ProblemCard(reportType = "Blocked/Damaged Trail", associatedImage = R.drawable.tree_down_icon)
            ProblemCard(reportType = "Trash on Trail", associatedImage = R.drawable.trash_icon)
            ProblemCard(reportType = "Aggressive Racoon", associatedImage = R.drawable.trash_icon)
            ProblemCard(reportType = "Heartbroken Dove", associatedImage = R.drawable.trash_icon)
            ProblemCard(reportType = "Aggressive Bear", associatedImage = R.drawable.trash_icon)
            ProblemCard(reportType = "Poop on trail", associatedImage = R.drawable.poop_icon )

        }
    }
}

@Preview
@Composable
fun ReportProblemFragmentPreview() {
    ATVolunteerAidTheme {
        ReportProblemFragment()
    }
}