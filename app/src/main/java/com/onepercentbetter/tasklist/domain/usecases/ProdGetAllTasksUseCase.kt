package com.onepercentbetter.tasklist.domain.usecases

import com.onepercentbetter.core.model.Task
import com.onepercentbetter.repository.task.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class
ProdGetAllTasksUseCase
    @Inject
    constructor(
        private val taskRepository: TaskRepository,
    ) : GetAllTasksUseCase {
        override fun invoke(): Flow<Result<List<Task>>> {
            return taskRepository.fetchAllTasks()
        }
    }
