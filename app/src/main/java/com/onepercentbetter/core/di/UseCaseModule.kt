package com.onepercentbetter.core.di

import com.onepercentbetter.login.domain.usecase.CredentialsLoginUseCase
import com.onepercentbetter.login.domain.usecase.ProdCredentialsLoginUseCase
import com.onepercentbetter.tasklist.domain.usecases.GetAllTasksUseCase
import com.onepercentbetter.tasklist.domain.usecases.ProdGetAllAllTasksUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * This module is responsible for defining the creation of any use cases dependencies
 * used in the application.
 *
 * NOTE: If this gets very large, we may want to consider refactoring and making modules
 * feature dependent.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindsCredentialsLoginUseCase(
        credentialsLoginUseCase: ProdCredentialsLoginUseCase
    ): CredentialsLoginUseCase

    @Binds
    abstract fun bindsGetAllTasksUseCase(
        getAllTasksUseCase: ProdGetAllAllTasksUseCase
    ): GetAllTasksUseCase
}
