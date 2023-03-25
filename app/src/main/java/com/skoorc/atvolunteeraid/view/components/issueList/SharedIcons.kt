package com.skoorc.atvolunteeraid.view.components.issueList

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skoorc.atvolunteeraid.MainActivityCompose
import com.skoorc.atvolunteeraid.R

@Composable
fun SortIcon() {
    Icon(
        painter = painterResource(id = R.drawable.sort),
        contentDescription = "Sort Icon",
        modifier = Modifier.size(30.dp, 30.dp)
    )
}

@Composable
fun SortButton(onClickFunction: () -> Unit) {
    Row() {
        Button(onClick = onClickFunction,
            modifier = Modifier
                .height(50.dp)
                .width(45.dp)
                .padding(0.dp),

            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(2.dp, Color.Gray),
        ) {
            SortIcon()
        }
    }
}

@Preview
@Composable
fun SortIconPreview() {
    SortButton(MainActivityCompose.placeholderClickAction("blah"))
}