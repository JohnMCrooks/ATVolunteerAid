package com.skoorc.atvolunteeraid

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.skoorc.atvolunteeraid.ui.theme.ATVolunteerAidTheme
import com.skoorc.atvolunteeraid.view.components.main.AtLogoImage
import com.skoorc.atvolunteeraid.view.components.main.MainButton
import com.skoorc.atvolunteeraid.view.components.problemReport.ProblemCard

class MainActivityCompose : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent(this.baseContext)
        }
    }

    companion object {
        const val TAG = "Main_Compose"
        internal fun placeholderClickAction(text: String): () -> Unit {
            return  { print(text) }
        }
        internal fun toaster(context: Context,text: String) : () -> Unit {
            val toast = Toast.makeText(context,  text, Toast.LENGTH_SHORT)
            return {
                toast.show()
                Log.d(TAG, text)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun MainContent(context: Context){
    Scaffold(
        content = { MyContent(context) }
    )
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun MyContent(context: Context) {
    ATVolunteerAidTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    AtLogoImage()
                    MainButton(
                        buttonText = "Issues",
                        onClickFunction = MainActivityCompose.toaster(
                            context,
                            "Issues button clicked"
                        )
                    )
                    MainButton(
                        "View Map",
                        onClickFunction = MainActivityCompose.toaster(
                            context,
                            "View map button clicked"
                        )
                    )
                    MainButton(
                        "Report \nan Issue",
                        onClickFunction = MainActivityCompose.toaster(
                            context,
                            "Report Issue button clicked"
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ATVolunteerAidTheme {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                AtLogoImage()
                MainButton(
                    "Issues",
                    MainActivityCompose.placeholderClickAction("View map button clicked")
                )
                MainButton(
                    "View Map",
                    MainActivityCompose.placeholderClickAction("View map button clicked")
                )
                MainButton(
                    "Report an Issue",
                    MainActivityCompose.placeholderClickAction("Report Issue button clicked")
                )
                ProblemCard(reportType = "Poop", R.drawable.poop_icon)
                ProblemCard(reportType = "Trash", R.drawable.trash_icon)
                ProblemCard(reportType = "Signage", R.drawable.trail_blaze_icon)
            }
    }
}