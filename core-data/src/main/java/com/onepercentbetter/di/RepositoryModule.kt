package com.onepercentbetter.di

import com.onepercentbetter.repository.task.TaskRepositoryImpl
import com.onepercentbetter.core.datastore.token.TokenRepositoryImpl
import com.onepercentbetter.repository.login.LoginRepositoryImpl
import com.onepercentbetter.repository.login.LoginRepository
import com.onepercentbetter.core.datastore.token.TokenRepository
import com.onepercentbetter.repository.task.TaskRepository
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
    abstract fun bindLoginRepository(
        loginRepository: LoginRepositoryImpl,
    ): LoginRepository

    @Binds
    @Singleton
    abstract fun bindTaskListRepository(
        taskListRepository: TaskRepositoryImpl,
    ): TaskRepository
}
