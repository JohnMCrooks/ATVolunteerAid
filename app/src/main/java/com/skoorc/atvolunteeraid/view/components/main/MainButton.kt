package com.skoorc.atvolunteeraid.view.components.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skoorc.atvolunteeraid.MainActivityCompose
import com.skoorc.atvolunteeraid.ui.theme.ATVolunteerAidTheme

@Composable
fun MainButton(buttonText: String, onClickFunction: () -> Unit ) {
    val reportColor: Color = if (buttonText.contains("Report")) {
        Color.Red
    } else {
        MaterialTheme.colors.primary
    }
    Button(onClick = onClickFunction,
        modifier = Modifier
            .height(100.dp)
            .width(150.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, Color.Gray),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.primary
        )
    ){
        Text(
            text = buttonText,
            fontSize = 20.sp,
            color = reportColor,
            style = TextStyle(textAlign = TextAlign.Center)
        )
    }
}

@Preview
@Composable
fun MainButtonPreview() {
    ATVolunteerAidTheme {
        MainButton(
            "Button Text",
            MainActivityCompose.placeholderClickAction("View map button clicked")
        )
    }
}