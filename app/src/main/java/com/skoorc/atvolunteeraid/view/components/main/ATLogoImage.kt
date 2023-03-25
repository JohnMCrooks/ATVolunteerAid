package com.skoorc.atvolunteeraid.view.components.main

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.skoorc.atvolunteeraid.R

@Composable
fun AtLogoImage() {
    Image(
        painter = painterResource(id = R.drawable.at_logo),
        contentDescription = "AT trail Logo",
    )
}

@Preview
@Composable
private fun AtLogoPreview() {
    AtLogoImage()
}