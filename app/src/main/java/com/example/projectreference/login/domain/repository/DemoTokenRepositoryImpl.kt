package com.example.projectreference.login.domain.repository

import com.example.projectreference.login.domain.model.Token
import javax.inject.Inject

/**
 * This is a sample [TokenRepository] that does not interact with any real data source, but allows
 * us to quickly modify return values for manual testing.
 */
class DemoTokenRepositoryImpl @Inject constructor() : TokenRepository {
    override suspend fun storeToken(token: Token) {
        // No op
    }

    override suspend fun fetchToken(): Token? {
        return null
    }
}
