package com.onepercentbetter.repository.login

import com.onepercentbetter.core.model.AuthToken
import com.onepercentbetter.core.model.Credentials
import com.onepercentbetter.core.model.LoginResponse
import com.onepercentbetter.core.model.RefreshToken
import com.onepercentbetter.core.model.Token
import javax.inject.Inject

/**
 * This is a sample [LoginRepository] that does not interact with any real data source, but allows
 * us to quickly modify return values for manual testing.
 */
class LoginRepositoryImpl
    @Inject
    constructor() : LoginRepository {
        override suspend fun login(
            credentials: Credentials,
        ): Result<LoginResponse> {
            val defaultToken =
                Token(
                    AuthToken("DemoAuthToken"),
                    RefreshToken("DemoRefreshToken"),
                )

            val defaultResponse = LoginResponse(defaultToken)

            return Result.success(defaultResponse)
        }
    }
