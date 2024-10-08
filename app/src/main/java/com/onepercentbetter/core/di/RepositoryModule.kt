package com.onepercentbetter.core.di

import com.onepercentbetter.core.data.local.RoomTaskRepository
import com.onepercentbetter.login.domain.repository.DataStoreTokenRepository
import com.onepercentbetter.login.domain.repository.DemoLoginRepositoryImpl
import com.onepercentbetter.login.domain.repository.LoginRepository
import com.onepercentbetter.login.domain.repository.TokenRepository
import com.onepercentbetter.task.api.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * This module is responsible for defining the creation of any repository dependencies
 * used in the application.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindTokenRepository(
        tokenRepository: DataStoreTokenRepository,
    ): TokenRepository

    @Binds
    abstract fun bindLoginRepository(
        loginRepository: DemoLoginRepositoryImpl,
    ): LoginRepository

    @Binds
    @Singleton
    abstract fun bindTaskListRepository(
        taskListRepository: RoomTaskRepository,
    ): TaskRepository
}
