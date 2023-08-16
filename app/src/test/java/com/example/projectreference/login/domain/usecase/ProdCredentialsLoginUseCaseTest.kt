package com.example.projectreference.login.domain.usecase

import com.example.projectreference.login.domain.model.LoginResponse
import com.example.projectreference.core.data.Result
import com.example.projectreference.fakes.FakeLoginRepository
import com.example.projectreference.login.domain.model.Credentials
import com.example.projectreference.login.domain.model.Email
import com.example.projectreference.login.domain.model.InvalidCredentialsException
import com.example.projectreference.login.domain.model.LoginState
import com.example.projectreference.login.domain.model.Password
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ProdCredentialsLoginUseCaseTest {

    @Test
    fun testSuccessfulLogin() = runTest {
        val inputCredentials = Credentials(
            email = Email("leonardopontes.santana@gmail.com"),
            password = Password("123456")
        )

        val loginResponse = Result.Success(
            LoginResponse(
                authToken = "Success"
            )
        )

        val loginRepository = FakeLoginRepository().apply {
            mockLoginWithCredentials(
                inputCredentials,
                loginResponse
            )
        }

        val useCase = ProdCredentialsLoginUseCase(loginRepository.mock)
        val useCaseResult = useCase(inputCredentials)

        assertThat(useCaseResult).isEqualTo(LoginState.Success)
    }

    @Test
    fun testUnknownFailureLogin() = runTest {
        val inputCredentials = Credentials(
            email = Email("leonardopontes.santana@gmail.com"),
            password = Password("123456")
        )

        val loginResponse: Result<LoginResponse> = Result.Error(Throwable("Error"))

        val loginRepository = FakeLoginRepository().apply {
            mockLoginWithCredentials(
                inputCredentials,
                loginResponse
            )
        }

        val useCase = ProdCredentialsLoginUseCase(loginRepository.mock)
        val useCaseResult = useCase(inputCredentials)

        assertThat(useCaseResult).isEqualTo(LoginState.Failure.Unknown)
    }

    @Test
    fun testInvalidCredentialsLogin() = runTest {
        val inputCredentials = Credentials(
            email = Email("leonardopontes.santana@gmail.com"),
            password = Password("123456")
        )

        val loginResponse: Result<LoginResponse> = Result.Error(
            InvalidCredentialsException()
        )

        val loginRepository = FakeLoginRepository().apply {
            mockLoginWithCredentials(
                inputCredentials,
                loginResponse
            )
        }

        val useCase = ProdCredentialsLoginUseCase(loginRepository.mock)
        val useCaseResult = useCase(inputCredentials)

        assertThat(useCaseResult).isEqualTo(LoginState.Failure.InvalidCredentials)
    }
}