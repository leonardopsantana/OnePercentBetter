package com.example.projectreference

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectreference.core.ui.theme.TOATheme
import com.example.projectreference.login.domain.usecase.DemoCredentialsLoginUseCase
import com.example.projectreference.login.ui.LoginScreen
import com.example.projectreference.login.ui.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TOATheme {
                LoginScreen(
                    onLoginCompleted = {
                        Log.d("MainActivity", "Login has been completed")
                    }
                )
            }
        }
    }
}
