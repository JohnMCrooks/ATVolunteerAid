package com.skoorc.atvolunteeraid.view.components.issueList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skoorc.atvolunteeraid.MainActivityCompose
import com.skoorc.atvolunteeraid.model.Location
import com.skoorc.atvolunteeraid.ui.theme.ATVolunteerAidTheme
import com.skoorc.atvolunteeraid.util.MockUtil

@Composable
fun IssueListScrollableArea(issues: List<Location>) {
    Box(
        modifier = Modifier.fillMaxWidth()
        .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.padding(8.dp)) {
                issues.forEach { issue ->
                    IssueCard(
                        issue,
                        MainActivityCompose.toaster(LocalContext.current,"short click"),
                        MainActivityCompose.toaster(LocalContext.current, "Long click"))
            }
        }
    }
}

@Preview
@Composable
fun IssueListScrollableAreaPreview() {
    ATVolunteerAidTheme {
        IssueListScrollableArea(
            listOf(
                MockUtil().getRandomLocationReport(),
                MockUtil().getRandomLocationReport()))
    }
}