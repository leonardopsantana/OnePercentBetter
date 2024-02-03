package com.onepercentbetter.addtask.domain.usecase

import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.core.data.Result
import com.onepercentbetter.tasklist.domain.model.Task
import com.onepercentbetter.tasklist.domain.repository.TaskListRepository
import java.time.LocalDate
import javax.inject.Inject

class ProdAddTaskUseCase @Inject constructor(
    private val taskListRepository: TaskListRepository
) : AddTaskUseCase {
    override suspend fun invoke(task: Task): AddTaskResult {
        val validationResult = validateInput(task)

        validationResult?.let {
            return it
        }

        return when (taskListRepository.addTask(task)) {
            is Result.Error -> AddTaskResult.Failure.Unknown
            is Result.Success -> AddTaskResult.Success
        }
    }

    private fun validateInput(task: Task): AddTaskResult.Failure.InvalidInput? {
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
