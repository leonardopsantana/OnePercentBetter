package com.example.projectreference.login.domain.usecase

import com.example.projectreference.login.domain.model.LoginResult

@JvmInline
@Suppress("UnusedPrivateMember")
value class Email(private val email: String)

@JvmInline
@Suppress("UnusedPrivateMember")
value class Password(private val email: String)

/**
 * This use case consumes any information required to log in the user and attempts to do so.
 */
interface LoginUseCase {

    suspend operator fun invoke(
        email: Email,
        password: Password
    ): LoginResult
}
