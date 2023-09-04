package com.example.projectreference.login.ui

/**
 * This defines the state of the login screen
 *
 * @param[email] The current text entered in the email field.
 * @param [password] The current text entered in the password field.
 * @param [showProgress] True if we want to show a loading indicator on the screen, false otherwise.
 * @param [errorMessage] If supplied, an error message explaining why a user could not log in.
 * @param [emailInputErrorMessage] If supplied, an error message explaining the problem with the
 * e-mail input
 * @param [passwordEmailInputErrorMessage] If supplied, an error message explaining a problem with
 * the password input
 */
data class LoginViewState(
    val email: String,
    val password: String,
    val showProgress: Boolean = false,
    val errorMessage: String?,
    val emailInputErrorMessage: String?,
    val passwordEmailInputErrorMessage: String?
)
