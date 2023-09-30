package com.example.projectreference.login.domain.repository

import com.example.projectreference.core.data.Result
import com.example.projectreference.login.domain.model.AuthToken
import com.example.projectreference.login.domain.model.Credentials
import com.example.projectreference.login.domain.model.LoginResponse
import com.example.projectreference.login.domain.model.RefreshToken
import com.example.projectreference.login.domain.model.Token
import javax.inject.Inject

/**
 * This is a sample [LoginRepository] that does not interact with any real data source, but allows
 * us to quickly modify return values for manual testing.
 */
class DemoLoginRepositoryImpl @Inject constructor(): LoginRepository {
    override suspend fun login(credentials: Credentials): Result<LoginResponse> {
        val defaultToken = Token(
            AuthToken(""),
            RefreshToken("")
        )

        val defaultResponse = LoginResponse(defaultToken)

        return Result.Success(defaultResponse)
    }
}