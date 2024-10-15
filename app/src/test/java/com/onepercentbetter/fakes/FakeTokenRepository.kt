package com.onepercentbetter.fakes

import com.onepercentbetter.core.model.Token
import com.onepercentbetter.core.datastore.token.TokenRepository
import io.mockk.coVerify
import io.mockk.mockk

class FakeTokenRepository {
    val mock: com.onepercentbetter.core.datastore.token.TokenRepository = mockk(
        relaxUnitFun = true,
    )

    fun verifyTokenStored(
        token: Token,
    ) {
        coVerify {
            mock.storeToken(token)
        }
    }

    fun verifyNoTokenStored() {
        coVerify(exactly = 0) {
            mock.storeToken(any())
        }
    }
}
