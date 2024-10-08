package com.onepercentbetter.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.onepercentbetter.DataStoreToken
import com.onepercentbetter.core.data.local.tokenDataStore
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
}
