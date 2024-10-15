package com.onepercentbetter.repository.login

import com.onepercentbetter.core.model.Credentials
import com.onepercentbetter.core.model.LoginResponse

/**
 * The data layer to any requests related to logging in the user.
 */
interface LoginRepository {
    /**
     * Given some user [credentials], try to login the user.
     *
     * @param: A [Result] that contains the [LoginResponse] if successful, or an error otherwise
     */
    suspend fun login(
        credentials: Credentials,
    ): Result<LoginResponse>
}
