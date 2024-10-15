package com.onepercentbetter.tasklist.domain.usecases

import com.onepercentbetter.repository.task.TaskListResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Fetches all tasks for a given date.
 */
interface GetTasksForDateUseCase {
    operator fun invoke(
        date: LocalDate,
    ): Flow<TaskListResult>
}
