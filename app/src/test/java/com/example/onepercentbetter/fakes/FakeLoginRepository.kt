package com.example.onepercentbetter.fakes

import com.example.onepercentbetter.core.data.Result
import com.example.onepercentbetter.login.domain.model.Credentials
import com.example.onepercentbetter.login.domain.model.LoginResponse
import com.example.onepercentbetter.login.domain.repository.LoginRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

/**
 * A fake implementation of a [LoginRepository] that wraps all of our mock work.
 */
class FakeLoginRepository {
    val mock: LoginRepository = mockk()

    fun mockLoginWithCredentials(
        credentials: Credentials,
        result: Result<LoginResponse>
    ) {
        coEvery {
            mock.login(credentials)
        } returns result
    }

    fun verifyNoLoginCall() {
        coVerify(exactly = 0) {
            mock.login(any())
        }
    }
}
