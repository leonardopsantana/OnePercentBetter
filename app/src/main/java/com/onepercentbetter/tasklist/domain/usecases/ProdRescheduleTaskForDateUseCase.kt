package com.onepercentbetter.tasklist.domain.usecases

import com.onepercentbetter.core.model.Task
import com.onepercentbetter.task.api.TaskRepository
import com.onepercentbetter.toEpochMillis
import java.time.LocalDate
import javax.inject.Inject

/**
 * Concrete implementation of a [RescheduleTaskUseCase] that will save the task change
 * inside of the given repository.
 */
class ProdRescheduleTaskForDateUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) : RescheduleTaskUseCase {

    override suspend fun invoke(task: Task, newDate: LocalDate) {
        val updatedTask = task.copy(
            scheduledDateMillis = newDate.toEpochMillis()
        )

        taskRepository.updateTask(updatedTask)
    }
}
