package com.onepercentbetter.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * All this composable should manage is consuming a [viewModel], observing its state, and
 * proxying that through to the [LoginContent].
 */
@Composable
fun LoginScreen(
    onLoginCompleted: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val viewState = viewModel.viewState.collectAsState()

    LoginContent(
        viewState = viewState.value,
        onEmailChanged = viewModel::emailChanged,
        onPasswordChanged = viewModel::passwordChanged,
        onLoginClicked = viewModel::loginButtonClicked,
        onSignUpClicked = viewModel::signUpButtonClicked,
    )
}
