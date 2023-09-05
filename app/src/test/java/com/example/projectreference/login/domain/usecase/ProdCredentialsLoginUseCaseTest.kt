package com.example.projectreference.login.domain.usecase

import com.example.projectreference.core.data.Result
import com.example.projectreference.fakes.FakeLoginRepository
import com.example.projectreference.fakes.FakeTokenRepository
import com.example.projectreference.login.domain.model.AuthToken
import com.example.projectreference.login.domain.model.Credentials
import com.example.projectreference.login.domain.model.Email
import com.example.projectreference.login.domain.model.InvalidCredentialsException
import com.example.projectreference.login.domain.model.LoginResponse
import com.example.projectreference.login.domain.model.LoginResult
import com.example.projectreference.login.domain.model.Password
import com.example.projectreference.login.domain.model.RefreshToken
import com.example.projectreference.login.domain.model.Token
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ProdCredentialsLoginUseCaseTest {

    private val defaultCredentials = Credentials(
        email = Email("leonardopontes.santana@gmail.com"),
        password = Password("123456")
    )

    private val defaultToken = Token(
        authToken = AuthToken("Auth"),
        refreshToken = RefreshToken("Refresh")
    )

    private lateinit var loginRepository: FakeLoginRepository
    private lateinit var tokenRepository: FakeTokenRepository

    @Before
    fun setUp() {
        loginRepository = FakeLoginRepository()
        tokenRepository = FakeTokenRepository()
    }

    @Test
    fun testSuccessfulLogin() = runTest {
        val loginResponse = Result.Success(
            LoginResponse(
                token = defaultToken
            )
        )

        loginRepository.apply {
            mockLoginWithCredentials(
                defaultCredentials,
                loginResponse
            )
        }

        val useCase = ProdCredentialsLoginUseCase(
            loginRepository = loginRepository.mock,
            tokenRepository = tokenRepository.mock
        )
        val useCaseResult = useCase(defaultCredentials)

        assertThat(useCaseResult).isEqualTo(LoginResult.Success)
        tokenRepository.verifyTokenStore(defaultToken)
    }

    @Test
    fun testUnknownFailureLogin() = runTest {
        val loginResponse: Result<LoginResponse> = Result.Error(Throwable("Error"))

        val loginRepository = loginRepository.apply {
            mockLoginWithCredentials(
                defaultCredentials,
                loginResponse
            )
        }

        val tokenRepository = tokenRepository

        val useCase = ProdCredentialsLoginUseCase(
            loginRepository = loginRepository.mock,
            tokenRepository = tokenRepository.mock
        )

        val useCaseResult = useCase(defaultCredentials)

        assertThat(useCaseResult).isEqualTo(LoginResult.Failure.Unknown)
        tokenRepository.verifyNoTokenStore()
    }

    @Test
    fun testInvalidCredentialsLogin() = runTest {
        val loginResponse: Result<LoginResponse> = Result.Error(
            InvalidCredentialsException()
        )

        loginRepository.apply {
            mockLoginWithCredentials(
                defaultCredentials,
                loginResponse
            )
        }

        val useCase = ProdCredentialsLoginUseCase(
            loginRepository = loginRepository.mock,
            tokenRepository = tokenRepository.mock
        )

        val useCaseResult = useCase(defaultCredentials)

        assertThat(useCaseResult).isEqualTo(LoginResult.Failure.InvalidCredentials)
        tokenRepository.verifyNoTokenStore()
    }
}
