package com.onepercentbetter.tasklist.domain.usecases

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.tasklist.domain.model.Task

/**
 * Fetches all tasks that the user has created.
 */
interface GetAllTasksUseCase {
    suspend operator fun invoke(): Result<List<Task>>
}
