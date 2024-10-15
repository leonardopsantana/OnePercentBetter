package com.onepercentbetter.core.datastore.di

import com.onepercentbetter.core.datastore.AndroidPreferences
import com.onepercentbetter.core.datastore.Preferences
import com.onepercentbetter.core.datastore.token.TokenRepository
import com.onepercentbetter.core.datastore.token.TokenRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun bindTokenRepository(
        tokenRepository: TokenRepositoryImpl,
    ): TokenRepository

    @Binds
    abstract fun bindPreferences(
        preferences: AndroidPreferences,
    ): Preferences
}
