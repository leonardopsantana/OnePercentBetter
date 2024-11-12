package com.onepercentbetter.login.domain.usecase

import com.onepercentbetter.core.model.AuthToken
import com.onepercentbetter.core.model.Credentials
import com.onepercentbetter.core.model.Email
import com.onepercentbetter.core.model.Password
import com.onepercentbetter.core.model.RefreshToken
import com.onepercentbetter.core.model.Token
import com.onepercentbetter.fakes.FakeLoginRepository
import com.onepercentbetter.fakes.FakeTokenRepository
import org.junit.Before

class ProdCredentialsLoginUseCaseTest {
    private val defaultCredentials =
        Credentials(
            email = Email("leonardopontes.santana@gmail.com"),
            password = Password("123456"),
        )

    private val defaultToken =
        Token(
            authToken = AuthToken("Auth"),
            refreshToken = RefreshToken("Refresh"),
        )

    private lateinit var loginRepository: FakeLoginRepository
    private lateinit var tokenRepository: FakeTokenRepository

    @Before
    fun setUp() {
        loginRepository = FakeLoginRepository()
        tokenRepository = FakeTokenRepository()
    }

    //    @Test
    //    fun testSuccessfulLogin() =
    //        runTest {
    //            val loginResponse =
    //                Result.Success(
    //                    LoginResponse(
    //                        token = defaultToken,
    //                    ),
    //                )
    //
    //            loginRepository.apply {
    //                mockLoginWithCredentials(
    //                    defaultCredentials,
    //                    loginResponse,
    //                )
    //            }
    //
    //            val useCase =
    //                ProdCredentialsLoginUseCase(
    //                    loginRepository = loginRepository.mock,
    //                    tokenRepository = tokenRepository.mock,
    //                )
    //            val useCaseResult = useCase.login(defaultCredentials)
    //
    //            assertThat(useCaseResult).isEqualTo(LoginResult.Success)
    //            tokenRepository.verifyTokenStore(defaultToken)
    //        }
    //
    //    @Test
    //    fun testUnknownFailureLogin() =
    //        runTest {
    //            val loginResponse: Result<LoginResponse> = Result.Error(Throwable("Error"))
    //
    //            val loginRepository =
    //                loginRepository.apply {
    //                    mockLoginWithCredentials(
    //                        defaultCredentials,
    //                        loginResponse,
    //                    )
    //                }
    //
    //            val tokenRepository = tokenRepository
    //
    //            val useCase =
    //                ProdCredentialsLoginUseCase(
    //                    loginRepository = loginRepository.mock,
    //                    tokenRepository = tokenRepository.mock,
    //                )
    //
    //            val useCaseResult = useCase.login(defaultCredentials)
    //
    //            assertThat(useCaseResult).isEqualTo(LoginResult.Failure.Unknown)
    //            tokenRepository.verifyNoTokenStore()
    //        }
    //
    //    @Test
    //    fun testInvalidCredentialsLogin() =
    //        runTest {
    //            val loginResponse: Result<LoginResponse> =
    //                Result.Error(
    //                    InvalidCredentialsException(),
    //                )
    //
    //            loginRepository.apply {
    //                mockLoginWithCredentials(
    //                    defaultCredentials,
    //                    loginResponse,
    //                )
    //            }
    //
    //            val useCase =
    //                ProdCredentialsLoginUseCase(
    //                    loginRepository = loginRepository.mock,
    //                    tokenRepository = tokenRepository.mock,
    //                )
    //
    //            val useCaseResult = useCase.login(defaultCredentials)
    //
    //            assertThat(useCaseResult).isEqualTo(LoginResult.Failure.InvalidCredentials)
    //            tokenRepository.verifyNoTokenStore()
    //        }
    //
    //    @Test
    //    fun testEmptyCredentialsLogin() =
    //        runTest {
    //            val emptyCredentials = Credentials()
    //
    //            val useCase =
    //                ProdCredentialsLoginUseCase(
    //                    loginRepository = loginRepository.mock,
    //                    tokenRepository = tokenRepository.mock,
    //                )
    //
    //            val result = useCase.login(emptyCredentials)
    //            assertThat(result).isEqualTo(
    //                LoginResult.Failure.EmptyCredentials(
    //                    emptyEmail = true,
    //                    emptyPassword = true,
    //                ),
    //            )
    //            loginRepository.verifyNoLoginCall()
    //            tokenRepository.verifyNoTokenStore()
    //        }
}
