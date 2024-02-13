package com.onepercentbetter.tasklist.domain.usecases

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.tasklist.domain.model.Task
import com.onepercentbetter.tasklist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProdGetAllTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) : GetAllTasksUseCase {
    override fun invoke(): Flow<Result<List<Task>>> {
        return taskRepository.fetchAllTasks()
    }
}
