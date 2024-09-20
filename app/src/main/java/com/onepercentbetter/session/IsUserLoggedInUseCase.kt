package com.onepercentbetter.session

import com.onepercentbetter.login.domain.repository.TokenRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

/**
 * A use case to extract the domain logic to check if a user is currently signed in
 * on this device.
 */
class IsUserLoggedInUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {

    suspend fun isUserLoggedIn(): Boolean {
        val authToken = tokenRepository.observeToken()?.firstOrNull()
        return authToken != null
    }
}
