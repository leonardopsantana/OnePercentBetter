package com.onepercentbetter.tasklist.domain.usecases

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.tasklist.domain.model.Task
import com.onepercentbetter.tasklist.domain.repository.TaskListRepository
import javax.inject.Inject

class ProdGetAllAllTasksUseCase @Inject constructor(
    private val taskListRepository: TaskListRepository
) : GetAllTasksUseCase {
    override suspend fun invoke(): Result<List<Task>> {
        return taskListRepository.fetchAllTasks()
    }
}
