package com.skoorc.atvolunteeraid.view.components.issueList

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skoorc.atvolunteeraid.MainActivityCompose

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun IssueFilterButton(buttonText: String, onClickFunction: () -> Unit) {
    Button(onClick = onClickFunction,
        modifier = Modifier
            .height(50.dp)
            .width(150.dp)
            .padding(start = 8.dp, end = 8.dp),

        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, Color.Gray),
    ){
        Text(
            text = buttonText,
            fontSize = 20.sp,
            style = TextStyle(textAlign = TextAlign.Center)
        )
    }
}


@RequiresApi(Build.VERSION_CODES.P)
@Preview
@Composable
fun IssueFilterButtonPreview() {
    IssueFilterButton("Filter: All", MainActivityCompose.placeholderClickAction("stuff"))
}