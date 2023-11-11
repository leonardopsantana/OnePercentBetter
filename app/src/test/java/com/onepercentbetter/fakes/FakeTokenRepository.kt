package com.onepercentbetter.fakes

import com.onepercentbetter.login.domain.model.Token
import com.onepercentbetter.login.domain.repository.TokenRepository
import io.mockk.coVerify
import io.mockk.mockk

/**
 * A fake implementation of a [TokenRepository] that wraps all of our mock work.
 */
class FakeTokenRepository {
    val mock: TokenRepository = mockk(
        relaxUnitFun = true
    )

    fun verifyTokenStore(token: Token) {
        coVerify {
            mock.storeToken(token)
        }
    }

    fun verifyNoTokenStore() {
        coVerify(exactly = 0) {
            mock.storeToken(any())
        }
    }
}
