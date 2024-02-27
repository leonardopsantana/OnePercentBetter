package com.onepercentbetter.addtask.domain.usecase

import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.core.data.Result
import com.onepercentbetter.core_model.Task
import com.onepercentbetter.tasklist.domain.repository.TaskRepository
import java.time.LocalDate
import javax.inject.Inject

class ProdAddTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) : AddTaskUseCase {
    override suspend fun invoke(task: com.onepercentbetter.core_model.Task): AddTaskResult {
        val validationResult = validateInput(task)

        validationResult?.let {
            return it
        }

        return when (taskRepository.addTask(task)) {
            is Result.Error -> AddTaskResult.Failure.Unknown
            is Result.Success -> AddTaskResult.Success
        }
    }

    private fun validateInput(task: com.onepercentbetter.core_model.Task): AddTaskResult.Failure.InvalidInput? {
        val emptyDescription = task.description.isEmpty()
        val scheduledDateInPast = task.scheduledDate.isBefore(LocalDate.now())

        return if (emptyDescription || scheduledDateInPast) {
            AddTaskResult.Failure.InvalidInput(
                emptyDescription = emptyDescription,
                scheduledDateInPast = scheduledDateInPast
            )
        } else {
            null
        }
    }
}
