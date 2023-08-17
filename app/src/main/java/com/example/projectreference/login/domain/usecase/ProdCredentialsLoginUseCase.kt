package com.example.projectreference.login.domain.usecase

import com.example.projectreference.core.data.Result
import com.example.projectreference.login.domain.model.AuthToken
import com.example.projectreference.login.domain.model.Credentials
import com.example.projectreference.login.domain.model.InvalidCredentialsException
import com.example.projectreference.login.domain.model.LoginResponse
import com.example.projectreference.login.domain.model.LoginState
import com.example.projectreference.login.domain.model.RefreshToken
import com.example.projectreference.login.domain.model.Token
import com.example.projectreference.login.domain.repository.TokenRepository
import com.example.projectreference.login.domain.repository.LoginRepository

/**
 * A concrete implementation of a [CredentialsLoginUseCase] that will request logging in
 * via the [loginRepository]
 */
class ProdCredentialsLoginUseCase(
    private val loginRepository: LoginRepository,
    private val tokenRepository: TokenRepository
) : CredentialsLoginUseCase {
    override suspend fun invoke(credentials: Credentials): LoginState {
        return when (val repoResult = loginRepository.login(credentials)) {
            is Result.Success -> {
                tokenRepository.storeToken(
                    repoResult.data.token
                )
                LoginState.Success
            }

            is Result.Error -> {
                loginResultForError(repoResult)
            }
        }
    }

    /**
     * Checks the possible error scenarios for the [repoResult] and maps to an appropriate
     * [LoginState.Failure].
     */
    private fun loginResultForError(repoResult: Result.Error<LoginResponse>) =
        when (repoResult.error) {
            is InvalidCredentialsException -> {
                LoginState.Failure.InvalidCredentials
            }

            else -> {
                LoginState.Failure.Unknown
            }
        }
}
