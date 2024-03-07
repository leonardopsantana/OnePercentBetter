package com.onepercentbetter.tasklist.domain.usecases

import com.onepercentbetter.core_model.Task
import com.onepercentbetter.core_data.Result

/**
 * Given a task, mark it as completed so it's no longer shown in the todo list.
 */
interface MarkTaskAsCompleteUseCase {

    suspend operator fun invoke(task: Task): Result<Unit>
}
