package com.onepercentbetter.tasklist.ui

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.tasklist.domain.usecases.MarkTaskAsCompleteUseCase

class FakeMarkTaskAsCompleteUseCase : MarkTaskAsCompleteUseCase {

    override suspend fun invoke(task: Task): Result<Unit> {
        TODO("Not yet implemented")
    }
}
