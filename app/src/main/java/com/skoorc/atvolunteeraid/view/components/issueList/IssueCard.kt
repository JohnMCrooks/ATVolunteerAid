package com.skoorc.atvolunteeraid.view.components.issueList


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skoorc.atvolunteeraid.MainActivityCompose
import com.skoorc.atvolunteeraid.model.Location
import com.skoorc.atvolunteeraid.ui.theme.ATVolunteerAidTheme
import com.skoorc.atvolunteeraid.util.MockUtil

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IssueCard(issue: Location, onClickFunction: () -> Unit, onLongClickFunction: () -> Unit) {
    Box(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
        Card(modifier = Modifier.fillMaxWidth().combinedClickable(
            onClick = {
                onClickFunction.invoke()
            },
            onLongClick = {
                onLongClickFunction.invoke()
            })
        ) {
            Row(modifier = Modifier.padding(8.dp)) {
                /// ID, Date, Issue Type, Status
                Column(modifier = Modifier.fillMaxWidth(0.45f)) {
                    Row() {
                        Text(text = "#", Modifier.padding(end = 2.dp))
                        Text(text =  issue.id.toString(), fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 16.dp))
                        Text(text = issue.date)
                    }
                    Text(text = "Issue Type: ", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 8.dp))
                    Text(text =  issue.type, modifier = Modifier.padding(start = 16.dp))
                    Row( modifier = Modifier.padding(start = 8.dp)) {
                        Text(text = "Status: ", fontWeight = FontWeight.Bold)
                        Text(text =  issue.status)
                    }
                }
                /// Reported By, Lat, Long
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row() {
                        Text(text = "Reported By: ", fontWeight = FontWeight.Bold)
                    }
                    Text(text =  issue.reportedBy, modifier = Modifier.fillMaxWidth().padding(start = 8.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Lat: ", fontWeight = FontWeight.Bold)
                        Text(text =  issue.latitude, modifier = Modifier.fillMaxWidth())
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Long: ", fontWeight = FontWeight.Bold)
                        Text(text =  issue.longitude, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun IssueCardPreview() {
    ATVolunteerAidTheme {
        IssueCard(
            MockUtil().getRandomLocationReport(),
            MainActivityCompose.placeholderClickAction("Short Click"),
            MainActivityCompose.placeholderClickAction("Long Click"))
    }
}