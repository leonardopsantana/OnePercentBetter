package com.onepercentbetter.fakes

import com.onepercentbetter.login.domain.model.Credentials
import com.onepercentbetter.login.domain.model.LoginResult
import io.mockk.coEvery
import io.mockk.mockk

class FakeCredentialsLoginUseCase {
    val mock: CredentialsLoginUseCase = mockk()

    /**
     * Emits the supplied [result]
     */
    fun mockLoginResultForCredentials(
        credentials: Credentials,
        result: LoginResult,
    ) {
        coEvery {
            mock(credentials)
        } returns result
    }
}
