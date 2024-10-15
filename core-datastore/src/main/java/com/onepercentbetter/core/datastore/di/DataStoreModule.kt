package com.onepercentbetter.core.datastore.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import com.onepercentbetter.DataStoreToken
import com.onepercentbetter.core.datastore.tokenDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    fun provideTokenDataStore(
        @ApplicationContext
        applicationContext: Context,
    ): DataStore<DataStoreToken> {
        return applicationContext.tokenDataStore
    }

    @Provides
    fun provideSharedPreferences(
        @ApplicationContext
        applicationContext: Context,
    ): SharedPreferences {
        return applicationContext.getSharedPreferences(
            "opb_preferences",
            Context.MODE_PRIVATE,
        )
    }
}
