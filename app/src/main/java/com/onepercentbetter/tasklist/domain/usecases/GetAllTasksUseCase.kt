package com.onepercentbetter.tasklist.domain.usecases

import com.onepercentbetter.core_data.Result
import com.onepercentbetter.core_model.Task
import kotlinx.coroutines.flow.Flow

/**
 * Fetches all tasks that the user has created.
 */
interface GetAllTasksUseCase {
    operator fun invoke(): Flow<Result<List<Task>>>
}
