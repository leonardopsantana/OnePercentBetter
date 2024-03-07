package com.onepercentbetter.tasklist.domain.usecases

import com.onepercentbetter.core_model.Task
import com.onepercentbetter.task_api.TaskRepository
import com.onepercentbetter.core_data.Result
import javax.inject.Inject

/**
 * A concrete implementation of [MarkTaskAsCompleteUseCase] which will modify the task
 * and update it inside our [taskRepository].
 */
class ProdMarkTaskAsCompletedUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) : MarkTaskAsCompleteUseCase {

    override suspend fun invoke(task: Task): Result<Unit> {
        val completedTask = task.copy(completed = true)

        return taskRepository.updateTask(completedTask)
    }
}
