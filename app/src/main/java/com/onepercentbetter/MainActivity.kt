package com.onepercentbetter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.onepercentbetter.core.ui.theme.OnePercentBetterTheme
import com.onepercentbetter.login.ui.LoginScreen
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight

            SideEffect {
                //Update all of the system bar colors to be transparent, and use
                //dark icons if we're in light theme
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcons
                )
            }

            setContent {
                OnePercentBetterTheme {
                    ProvideWindowInsets {
                        LoginScreen(
                            onLoginCompleted = {
                                Log.d("MainActivity", "Login has been completed")
                            }
                        )
                    }
                }
            }
        }
    }
}
