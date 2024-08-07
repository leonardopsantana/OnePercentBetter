package com.onepercentbetter.login.domain.usecase

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.login.domain.model.Credentials
import com.onepercentbetter.login.domain.model.InvalidCredentialsException
import com.onepercentbetter.login.domain.model.LoginResponse
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
        suspend fun login(credentials: Credentials): LoginResult {
            val validationResult = validateCredentials(credentials)

            if (validationResult != null) {
                return validationResult
            }

            return when (val repoResult = loginRepository.login(credentials)) {
                is Result.Success -> {
                    tokenRepository.storeToken(
                        repoResult.data.token,
                    )
                    LoginResult.Success
                }

                is Result.Error -> {
                    loginResultForError(repoResult)
                }
            }
        }

        /**
         * Given some [currentCredentials], ensure that we've been provided valid information that can used
         * to log in. If not, update the current [viewState] accordingly, and return whether or not to
         * to proceed.
         */
        private fun validateCredentials(credentials: Credentials): LoginResult.Failure.EmptyCredentials? {
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
         * Checks the possible error scenarios for the [repoResult] and maps to an appropriate
         * [LoginResult.Failure].
         */
        private fun loginResultForError(repoResult: Result.Error<LoginResponse>) =
            when (repoResult.error) {
                is InvalidCredentialsException -> {
                    LoginResult.Failure.InvalidCredentials
                }

                else -> {
                    LoginResult.Failure.Unknown
                }
            }
    }
