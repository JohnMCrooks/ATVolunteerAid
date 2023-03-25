package com.skoorc.atvolunteeraid.view.components.problemReport

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skoorc.atvolunteeraid.R
import com.skoorc.atvolunteeraid.ui.theme.ATVolunteerAidTheme
import com.skoorc.atvolunteeraid.util.Toaster

@Composable
fun ProblemCard(reportType: String, associatedImage: Int) {
    val context = LocalContext.current
    ATVolunteerAidTheme {
        Card(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            elevation = 10.dp,
            shape = RoundedCornerShape(size = 12.dp)

        ) {
            Box(
                modifier = Modifier.clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(true, 250.dp, Color.Cyan),
                onClick = {
                    //TODO: add proper nav on click
                    Log.i("PROBLEM_CARD_COMPOSABLE", " report card clicked")
                    Toaster().showToast(context, "$reportType clicked", 1)
                })) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        ProblemIcon(associatedImage, reportType)
                        Spacer(modifier = Modifier.width(width = 8.dp)) // gap between image and text
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.W900,
                                        color = Color(0xFF4552B8)
                                    )
                                ) {
                                    append(reportType)
                                }
                            },
                        )
                    }
            }

        }
    }
}

@Preview
@Composable
fun ProblemCardPreview() {
    ATVolunteerAidTheme {
        ProblemCard("Poop on the trail", R.drawable.at_logo)
    }
}

