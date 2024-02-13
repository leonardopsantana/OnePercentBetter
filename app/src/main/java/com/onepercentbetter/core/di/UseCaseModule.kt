package com.onepercentbetter.core.di

import com.onepercentbetter.addtask.domain.usecase.AddTaskUseCase
import com.onepercentbetter.addtask.domain.usecase.ProdAddTaskUseCase
import com.onepercentbetter.login.domain.usecase.CredentialsLoginUseCase
import com.onepercentbetter.login.domain.usecase.ProdCredentialsLoginUseCase
import com.onepercentbetter.tasklist.domain.usecases.GetAllTasksUseCase
import com.onepercentbetter.tasklist.domain.usecases.GetTasksForDateUseCase
import com.onepercentbetter.tasklist.domain.usecases.MarkTaskAsCompleteUseCase
import com.onepercentbetter.tasklist.domain.usecases.ProdGetAllTasksForDateUseCase
import com.onepercentbetter.tasklist.domain.usecases.ProdGetAllTasksUseCase
import com.onepercentbetter.tasklist.domain.usecases.ProdMarkTaskAsCompletedUseCase
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
        getAllTasksUseCase: ProdGetAllTasksUseCase
    ): GetAllTasksUseCase

    @Binds
    abstract fun bindAddTaskUseCase(
        addTaskUseCase: ProdAddTaskUseCase
    ): AddTaskUseCase

    @Binds
    abstract fun bindGetTasksForDateUseCase(
        getTasksForDateUseCase: ProdGetAllTasksForDateUseCase
    ): GetTasksForDateUseCase

    @Binds
    abstract fun bindTasksMarkTaskAsCompleteUseCase(
        prodMarkTaskAsCompletedUseCase: ProdMarkTaskAsCompletedUseCase
    ): MarkTaskAsCompleteUseCase
}
