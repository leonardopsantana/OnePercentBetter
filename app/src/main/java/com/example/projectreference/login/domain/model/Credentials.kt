package com.example.projectreference.login.domain.model

@JvmInline
@Suppress("UnusedPrivateMember")
value class Email(private val email: String)

@JvmInline
@Suppress("UnusedPrivateMember")
value class Password(private val email: String)

data class Credentials(
    val email: Email,
    val password: Password
)
