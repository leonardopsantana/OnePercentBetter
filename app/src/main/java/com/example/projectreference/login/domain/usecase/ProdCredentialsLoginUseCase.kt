package com.example.projectreference.login.domain.usecase

import com.example.projectreference.core.data.Result
import com.example.projectreference.login.domain.model.Credentials
import com.example.projectreference.login.domain.model.InvalidCredentialsException
import com.example.projectreference.login.domain.model.LoginResponse
import com.example.projectreference.login.domain.model.LoginResult
import com.example.projectreference.login.domain.repository.LoginRepository
import com.example.projectreference.login.domain.repository.TokenRepository

/**
 * A concrete implementation of a [CredentialsLoginUseCase] that will request logging in
 * via the [loginRepository]
 */
class ProdCredentialsLoginUseCase(
    private val loginRepository: LoginRepository,
    private val tokenRepository: TokenRepository
) : CredentialsLoginUseCase {
    override suspend fun invoke(credentials: Credentials): LoginResult {
        val validationResult = validateCredentials(credentials)

        if (validationResult != null) {
            return validationResult
        }

        return when (val repoResult = loginRepository.login(credentials)) {
            is Result.Success -> {
                tokenRepository.storeToken(
                    repoResult.data.token
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
