package com.skoorc.atvolunteeraid.view.components.problemReport

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.skoorc.atvolunteeraid.R

@Composable
fun ProblemIcon(imageResourceID: Int, contentDescription: String) {
    Image(
        painter = painterResource(id = imageResourceID),
        contentDescription = contentDescription,
    )
}

@Preview
@Composable
private fun ProblemIconPreview() {
    ProblemIcon(R.drawable.poop_icon, "Sample image")
}