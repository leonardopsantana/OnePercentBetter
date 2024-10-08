package com.onepercentbetter.login.domain.usecase

import com.onepercentbetter.login.domain.model.Credentials
import com.onepercentbetter.login.domain.model.InvalidCredentialsException
import com.onepercentbetter.login.domain.model.LoginResult
import com.onepercentbetter.login.domain.repository.LoginRepository
import com.onepercentbetter.login.domain.repository.TokenRepository
import javax.inject.Inject

/**
 * The [ProdCredentialsLoginUseCase] is used to login a user with a given set of credentials.
 * We will login via the supplied [loginRepository], and store the retrieved auth information inside
 * our [tokenRepository].
 */
class ProdCredentialsLoginUseCase
    @Inject
    constructor(
        private val loginRepository: LoginRepository,
        private val tokenRepository: TokenRepository,
    ) {
        suspend fun login(
            credentials: Credentials,
        ): LoginResult {
            val validationResult = validateCredentials(credentials)

            if (validationResult != null) {
                return validationResult
            }

            val result = loginRepository.login(credentials)

            return result.fold(
                onSuccess = { loginResponse ->
                    tokenRepository.storeToken(
                        loginResponse.token,
                    )
                    LoginResult.Success
                },
                onFailure = {
                    loginResultForError(it)
                },
            )
        }

        /**
         * Given some [currentCredentials], ensure that we've been provided valid information that can used
         * to log in. If not, update the current [viewState] accordingly, and return whether or not to
         * to proceed.
         */
        private fun validateCredentials(
            credentials: Credentials,
        ): LoginResult.Failure.EmptyCredentials? {
            val emptyEmail = credentials.email.value.isEmpty()
            val emptyPassword = credentials.password.value.isEmpty()

            return if (emptyEmail || emptyPassword) {
                LoginResult.Failure.EmptyCredentials(
                    emptyEmail = emptyEmail,
                    emptyPassword = emptyPassword,
                )
            } else {
                null
            }
        }

        /**
         * Checks the possible error scenarios for the [error] and maps to an appropriate
         * [LoginResult.Failure].
         */
        private fun loginResultForError(
            error: Throwable,
        ) = when (error) {
            is InvalidCredentialsException -> {
                LoginResult.Failure.InvalidCredentials
            }

            else -> {
                LoginResult.Failure.Unknown
            }
        }
    }
