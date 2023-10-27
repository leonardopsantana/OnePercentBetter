package com.example.projectreference

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.projectreference.core.ui.theme.TOATheme
import com.example.projectreference.login.ui.LoginScreen
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TOATheme {
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
