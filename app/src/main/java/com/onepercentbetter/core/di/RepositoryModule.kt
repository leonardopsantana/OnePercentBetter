package com.onepercentbetter.core.di

import com.onepercentbetter.login.domain.repository.DemoLoginRepositoryImpl
import com.onepercentbetter.login.domain.repository.DemoTokenRepositoryImpl
import com.onepercentbetter.login.domain.repository.LoginRepository
import com.onepercentbetter.login.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This module is responsible for defining the creation of any repository dependencies
 * used in the application.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTokenRepository(
        tokenRepository: DemoTokenRepositoryImpl,
    ): TokenRepository

    @Binds
    abstract fun bindLoginRepository(
        loginRepository: DemoLoginRepositoryImpl
    ): LoginRepository
}
