package com.example.projectreference.ui.login

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projectreference.R
import com.example.projectreference.ui.components.PrimaryButton
import com.example.projectreference.ui.components.SecondaryButton
import com.example.projectreference.ui.components.TOATextField
import com.example.projectreference.ui.core.VerticalSpacer
import com.example.projectreference.ui.theme.TOATheme

private const val APP_LOGO_WIDTH_PERCENTAGE = 0.75F

/**
 * This composable maintains the entire screen for handling user login
 *
 * @param:[viewState] The current state of the screen to render.
 * @param:[onUsernameChanged] A callback invoked when the user enters their username.
 * @param:[onPasswordChanged] A callback invoked when the user enters their password.
 * @param:[onLoginClicked] A callback invoked when the user clicks on login.
 * @param:[onSignUpClicked] A callback invoked when the user clicks on signup.
 */
@Composable
fun LoginContent(
    viewState: LoginViewState,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onSignUpClicked: () -> Unit
) {
    Surface(
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.screen_padding)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(1F))

            AppLogo()

            Spacer(modifier = Modifier.weight(1F))

            UsernameInput(text = viewState.userName, onTextChanged = onUsernameChanged)

            VerticalSpacer(height = 12.dp)

            PasswordInput(text = viewState.password, onTextChanged = onPasswordChanged)

            VerticalSpacer(height = 48.dp)

            LoginButton(onLoginClicked)

            VerticalSpacer(height = 12.dp)

            SignUpButton(onSignUpClicked)
        }
    }
}

@Composable
private fun SignUpButton(onSignUpClicked: () -> Unit) {
    SecondaryButton(
        text = stringResource(R.string.sign_up),
        onClick = onSignUpClicked,
    )
}

@Composable
private fun LoginButton(onLoginClicked: () -> Unit) {
    PrimaryButton(
        text = stringResource(R.string.log_in),
        onClick = onLoginClicked,
    )
}

@Composable
private fun PasswordInput(
    text: String,
    onTextChanged: (String) -> Unit
) {
    TOATextField(
        text = text,
        onTextChanged = onTextChanged,
        labelText = stringResource(R.string.password)
    )
}

@Composable
private fun UsernameInput(
    text: String,
    onTextChanged: (String) -> Unit
) {
    TOATextField(
        text = text,
        onTextChanged = onTextChanged,
        labelText = stringResource(R.string.username)
    )
}

@Composable
private fun AppLogo() {
    Image(
        painter =
        painterResource(id = R.drawable.ic_toa_checkmark),
        contentDescription = stringResource(R.string.app_logo_content_description),
        modifier = Modifier
            .fillMaxWidth(APP_LOGO_WIDTH_PERCENTAGE)
    )
}

@Preview(
    name = "Night mode - Empty",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day mode - Emprt",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun EmptyLoginContentPreview() {
    val viewState = LoginViewState(
        userName = "",
        password = ""
    )

    TOATheme {
        LoginContent(
            viewState = viewState,
            onUsernameChanged = {},
            onPasswordChanged = {},
            onLoginClicked = {},
            onSignUpClicked = {},
        )
    }
}
