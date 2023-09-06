package com.example.projectreference.login.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.projectreference.R
import com.example.projectreference.core.ui.components.PrimaryButton
import com.example.projectreference.core.ui.components.SecondaryButton
import com.example.projectreference.core.ui.components.TOATextField
import com.example.projectreference.core.ui.core.VerticalSpacer
import com.example.projectreference.core.ui.theme.TOATheme
import com.example.projectreference.login.domain.model.Credentials
import com.example.projectreference.login.domain.model.Email
import com.example.projectreference.login.domain.model.Password

private const val APP_LOGO_WIDTH_PERCENTAGE = 0.75F

/**
 * This composable maintains the entire screen for handling user login
 *
 * @param:[viewState] The current state of the screen to render.
 * @param:[onEmailChanged] A callback invoked when the user enters their email.
 * @param:[onPasswordChanged] A callback invoked when the user enters their password.
 * @param:[onLoginClicked] A callback invoked when the user clicks on login.
 * @param:[onSignUpClicked] A callback invoked when the user clicks on signup.
 */
@Composable
fun LoginContent(
    viewState: LoginViewState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onSignUpClicked: () -> Unit
) {
    Surface(
        color = MaterialTheme.colors.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LogoInputsColumn(
                viewState = viewState,
                onEmailChanged = onEmailChanged,
                onPasswordChanged = onPasswordChanged,
                onLoginClicked = onLoginClicked,
                onSignUpClicked = onSignUpClicked
            )

            CircularProgressIndicator(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun LogoInputsColumn(
    viewState: LoginViewState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onSignUpClicked: () -> Unit
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

        EmailInput(
            text = viewState.credentials.email.value,
            onTextChanged = onEmailChanged,
            errorMessage = (viewState as? LoginViewState.InputError)?.emailInputErrorMessage
        )

        VerticalSpacer(height = 12.dp)

        PasswordInput(
            text = viewState.credentials.password.value,
            onTextChanged = onPasswordChanged,
            errorMessage = (viewState as? LoginViewState.InputError)?.passwordEmailInputErrorMessage
        )

        if (viewState is LoginViewState.SubmissionError) {
            Text(
                text = viewState.errorMessage,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        VerticalSpacer(height = 48.dp)

        LoginButton(onLoginClicked = onLoginClicked, isEnabled = viewState.buttonsEnabled)

        VerticalSpacer(height = 12.dp)

        SignUpButton(onSignUpClicked = onSignUpClicked, isEnabled = viewState.buttonsEnabled)
    }
}

@Composable
private fun SignUpButton(onSignUpClicked: () -> Unit, isEnabled: Boolean) {
    SecondaryButton(
        text = stringResource(R.string.sign_up),
        onClick = onSignUpClicked,
    )
}

@Composable
private fun LoginButton(onLoginClicked: () -> Unit, isEnabled: Boolean) {
    PrimaryButton(
        text = stringResource(R.string.log_in),
        onClick = onLoginClicked,
        isEnabled = isEnabled
    )
}

@Composable
private fun PasswordInput(
    text: String,
    errorMessage: String?,
    onTextChanged: (String) -> Unit
) {
    TOATextField(
        text = text,
        onTextChanged = onTextChanged,
        labelText = stringResource(R.string.password),
        errorMessage = errorMessage,
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
private fun EmailInput(
    text: String,
    errorMessage: String?,
    onTextChanged: (String) -> Unit
) {
    TOATextField(
        text = text,
        onTextChanged = onTextChanged,
        labelText = stringResource(R.string.email),
        errorMessage = errorMessage
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
    name = "Day mode - Empty",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun EmptyLoginContentPreview(
    @PreviewParameter(LoginViewStateProvider::class)
    loginViewState: LoginViewState
) {
    TOATheme {
        LoginContent(
            viewState = loginViewState,
            onEmailChanged = {},
            onPasswordChanged = {},
            onLoginClicked = {},
            onSignUpClicked = {},
        )
    }
}

class LoginViewStateProvider : PreviewParameterProvider<LoginViewState> {
    override val values: Sequence<LoginViewState>
        get() {
            val activeCredentials = Credentials(
                Email("leonardopontes.santana@gmail.com"),
                Password("pass123")
            )

            return sequenceOf(
                LoginViewState.Initial,
                LoginViewState.Active(activeCredentials),
                LoginViewState.Submitting(activeCredentials),
                LoginViewState.SubmissionError(
                    credentials = activeCredentials,
                    errorMessage = "Something went wrong."
                ),
                LoginViewState.InputError(
                    credentials = activeCredentials,
                    emailInputErrorMessage = "Please enter an email.",
                    passwordEmailInputErrorMessage = "Please enter a password"
                ),
            )
        }
}