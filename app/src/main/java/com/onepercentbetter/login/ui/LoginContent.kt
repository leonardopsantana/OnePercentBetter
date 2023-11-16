package com.onepercentbetter.login.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.onepercentbetter.R
import com.onepercentbetter.core.ui.components.OPBTextField
import com.onepercentbetter.core.ui.components.PrimaryButton
import com.onepercentbetter.core.ui.components.SecondaryButton
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.core.ui.components.VerticalSpacer
import com.onepercentbetter.core.ui.components.getString
import com.onepercentbetter.core.ui.theme.OPBTheme
import com.onepercentbetter.login.domain.model.Credentials
import com.onepercentbetter.login.domain.model.Email
import com.onepercentbetter.login.domain.model.Password

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
        color = MaterialTheme.colorScheme.background
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

            if (viewState is LoginViewState.Submitting) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun LogoInputsColumn(
    viewState: LoginViewState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(dimensionResource(id = R.dimen.screen_padding))
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = contentPadding.calculateStartPadding(LocalLayoutDirection.current),
                end = contentPadding.calculateEndPadding(LocalLayoutDirection.current),
            )
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        VerticalSpacer(height = contentPadding.calculateTopPadding())

        AppLogo(modifier = Modifier.padding(88.dp))

        EmailInput(
            text = viewState.credentials.email.value,
            onTextChanged = onEmailChanged,
            errorMessage =
            (viewState as? LoginViewState.Active)?.emailInputErrorMessage?.getString(),
            enabled = viewState.inputsEnabled,

        )

        VerticalSpacer(height = 12.dp)

        PasswordInput(
            text = viewState.credentials.password.value,
            onTextChanged = onPasswordChanged,
            errorMessage = (viewState as? LoginViewState.Active)?.passwordInputErrorMessage?.getString(),
            enabled = viewState.inputsEnabled
        )

        if (viewState is LoginViewState.SubmissionError) {
            Text(
                text = viewState.errorMessage.getString(),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        VerticalSpacer(height = 48.dp)

        LoginButton(onLoginClicked = onLoginClicked, isEnabled = viewState.inputsEnabled)

        VerticalSpacer(height = 12.dp)

        SignUpButton(
            onSignUpClicked = onSignUpClicked,
            isEnabled = viewState.inputsEnabled,
            modifier = Modifier
                .padding(bottom = contentPadding.calculateBottomPadding())
                .navigationBarsPadding()
        )
    }
}

@Composable
private fun SignUpButton(onSignUpClicked: () -> Unit, isEnabled: Boolean, modifier: Modifier) {
    SecondaryButton(
        text = stringResource(R.string.sign_up),
        onClick = onSignUpClicked,
        isEnabled = isEnabled,
        modifier = modifier
    )
}

@Composable
private fun LoginButton(onLoginClicked: () -> Unit, isEnabled: Boolean) {
    PrimaryButton(
        text = stringResource(R.string.log_in),
        onClick = onLoginClicked,
        enabled = isEnabled
    )
}

@Composable
private fun PasswordInput(
    text: String,
    errorMessage: String?,
    onTextChanged: (String) -> Unit,
    enabled: Boolean,
) {
    OPBTextField(
        text = text,
        onTextChanged = onTextChanged,
        labelText = stringResource(R.string.password),
        errorMessage = errorMessage,
        visualTransformation = PasswordVisualTransformation(
            mask = '-'
        ),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        )
    )
}

@Composable
private fun EmailInput(
    text: String,
    errorMessage: String?,
    onTextChanged: (String) -> Unit,
    enabled: Boolean,
) {
    OPBTextField(
        text = text,
        onTextChanged = onTextChanged,
        labelText = stringResource(R.string.email),
        errorMessage = errorMessage,
        enabled = enabled,
    )
}

@Composable
private fun AppLogo(
    modifier: Modifier
) {
    Image(
        painter =
        painterResource(id = R.drawable.ic_brick),
        contentDescription = stringResource(R.string.app_logo_content_description),
        modifier = modifier
            .fillMaxWidth(APP_LOGO_WIDTH_PERCENTAGE),
        colorFilter = ColorFilter.tint(
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
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
    OPBTheme {
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
                LoginViewState.Active(
                    credentials = activeCredentials,
                ),
                LoginViewState.Submitting(activeCredentials),
                LoginViewState.SubmissionError(
                    credentials = activeCredentials,
                    errorMessage = UIText.StringText("Something went wrong.")
                ),
                LoginViewState.Active(
                    credentials = activeCredentials,
                    emailInputErrorMessage = UIText.StringText("Please enter an email."),
                    passwordInputErrorMessage = UIText.StringText("Please enter a password")
                ),
            )
        }
}
