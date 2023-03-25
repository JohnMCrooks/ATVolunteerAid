package com.skoorc.atvolunteeraid.view.components.issueList

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skoorc.atvolunteeraid.MainActivityCompose
import com.skoorc.atvolunteeraid.ui.theme.ATVolunteerAidTheme

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun IssueListHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Row() {
            Text( "Reports:", fontWeight = FontWeight.Bold, fontSize = 25.sp)
            Spacer(modifier = Modifier.width(width = 12.dp))
            Text(text = "29",  fontSize = 25.sp)
            Spacer(modifier = Modifier.width(width = 12.dp))
        }
       Row(modifier = Modifier
           .fillMaxWidth(),
           horizontalArrangement = Arrangement.End) {
           IssueFilterButton(
               buttonText = "Filter: All", onClickFunction = MainActivityCompose.toaster(
                   LocalContext.current, "Filter context change"
               )
           )
           SortButton(MainActivityCompose.toaster(LocalContext.current, "Sort Button Tapped"))
       }

    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview
@Composable
fun IssueListHeaderPreview() {
    ATVolunteerAidTheme {
        IssueListHeader()
    }
}