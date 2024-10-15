package com.onepercentbetter.addtask.domain.usecase

import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.core.model.Task
import kotlinx.coroutines.flow.Flow

/**
 * Given a new task, store that in the user's task list.
 */
interface AddTaskUseCase {
    suspend operator fun invoke(
        task: Task,
        ignoreTaskLimits: Boolean,
    ): Flow<AddTaskResult>
}
