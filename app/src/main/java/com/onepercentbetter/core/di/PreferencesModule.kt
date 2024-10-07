package com.onepercentbetter.core.di

import com.onepercentbetter.preferences.AndroidPreferences
import com.onepercentbetter.preferences.Preferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {
    @Binds
    abstract fun bindPreferences(
        preferences: AndroidPreferences,
    ): Preferences
}
