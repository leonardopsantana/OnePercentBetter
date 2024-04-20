package com.onepercentbetter.login.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.onepercentbetter.destinations.LoginScreenDestination
import com.onepercentbetter.destinations.TaskListScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

/**
 * All this composable should manage is consuming a [viewModel], observing its state, and
 * proxying that through to the [LoginContent].
 */
@Destination(
    start = true,
)
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val viewState = viewModel.viewState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(viewState.value) {
        coroutineScope.launch {
            viewModel.loginCompletedChannel.receive()
            navigator.navigate(TaskListScreenDestination) {
                this.popUpTo(LoginScreenDestination.route) {
                    this.inclusive = true
                }
            }
        }

        onDispose { }
    }

    val context = LocalContext.current

    LoginContent(
        viewState = viewState.value,
        onEmailChanged = viewModel::emailChanged,
        onPasswordChanged = viewModel::passwordChanged,
        onLoginClicked = viewModel::loginButtonClicked,
        onSignUpClicked = {
            Toast.makeText(
                context,
                "Not supported",
                Toast.LENGTH_SHORT,
            ).show()
        },
    )
}
