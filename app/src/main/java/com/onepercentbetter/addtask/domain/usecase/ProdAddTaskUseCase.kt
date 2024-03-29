package com.onepercentbetter.addtask.domain.usecase

import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.core.data.Result
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.task.api.TaskRepository
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class ProdAddTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) : AddTaskUseCase {
    override suspend fun invoke(task: Task): AddTaskResult {
        val sanitizedTask = task.copy(
            description = task.description.trim()
        )

        val validationResult = validateInput(sanitizedTask)

        if (validationResult != null) {
            return validationResult
        }

        return when (taskRepository.addTask(sanitizedTask)) {
            is Result.Error -> AddTaskResult.Failure.Unknown
            is Result.Success -> AddTaskResult.Success
        }
    }

    private fun validateInput(task: Task): AddTaskResult.Failure.InvalidInput? {
        val emptyDescription = task.description.isBlank()

        val scheduledDate = Instant
            .ofEpochMilli(task.scheduledDateMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        val scheduledDateInPast = scheduledDate.isBefore(LocalDate.now())

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
