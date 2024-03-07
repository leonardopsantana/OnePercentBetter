package com.onepercentbetter.tasklist.domain.usecases

import com.onepercentbetter.core_data.Result
import com.onepercentbetter.core_model.Task
import com.onepercentbetter.task_api.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProdGetAllTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) : GetAllTasksUseCase {
    override fun invoke(): Flow<Result<List<Task>>> {
        return taskRepository.fetchAllTasks()
    }
}
