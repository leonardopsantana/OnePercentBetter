package com.onepercentbetter.core.database.di

import android.content.Context
import androidx.room.Room
import com.onepercentbetter.core.database.OPBDatabase
import com.onepercentbetter.core.database.TaskDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideOPBDatabase(
        @ApplicationContext
        applicationContext: Context,
    ): OPBDatabase {
        return Room.databaseBuilder(
            applicationContext,
            OPBDatabase::class.java,
            "opb-database.db",
        ).build()
    }

    @Provides
    fun provideTaskDAO(
        database: OPBDatabase,
    ): TaskDAO {
        return database.taskDao()
    }
}
