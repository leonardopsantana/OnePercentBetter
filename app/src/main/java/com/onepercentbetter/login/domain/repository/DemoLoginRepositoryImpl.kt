package com.onepercentbetter.login.domain.repository

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.login.domain.model.AuthToken
import com.onepercentbetter.login.domain.model.Credentials
import com.onepercentbetter.login.domain.model.LoginResponse
import com.onepercentbetter.login.domain.model.RefreshToken
import com.onepercentbetter.login.domain.model.Token
import javax.inject.Inject

/**
 * This is a sample [LoginRepository] that does not interact with any real data source, but allows
 * us to quickly modify return values for manual testing.
 */
class DemoLoginRepositoryImpl
    @Inject
    constructor() : LoginRepository {
        override suspend fun login(credentials: Credentials): Result<LoginResponse> {
            val defaultToken =
                Token(
                    AuthToken("DemoAuthToken"),
                    RefreshToken("DemoRefreshToken"),
                )

            val defaultResponse = LoginResponse(defaultToken)

            return Result.Success(defaultResponse)
        }
    }
