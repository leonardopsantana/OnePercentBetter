package com.example.projectreference

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectreference.core.ui.components.PrimaryButton
import com.example.projectreference.core.ui.theme.TOATheme
import com.example.projectreference.login.domain.usecase.DemoCredentialsLoginUseCase
import com.example.projectreference.login.domain.usecase.ProdCredentialsLoginUseCase
import com.example.projectreference.login.ui.LoginScreen
import com.example.projectreference.login.ui.LoginViewModel

class MainActivity : ComponentActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private val loginViewModelFactory = object : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val useCase = DemoCredentialsLoginUseCase()

            return LoginViewModel(useCase) as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel =
            ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java)

        setContent {
            TOATheme {
                LoginScreen(viewModel = loginViewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name")
}
