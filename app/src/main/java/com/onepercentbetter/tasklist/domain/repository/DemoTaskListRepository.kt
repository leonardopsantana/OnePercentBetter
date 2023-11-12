package com.onepercentbetter.tasklist.domain.repository

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.tasklist.domain.model.Task
import kotlinx.coroutines.delay
import javax.inject.Inject

class DemoTaskListRepository @Inject constructor(): TaskListRepository {
    override suspend fun fetchAllTasks(): Result<List<Task>> {
        delay(2_000)

        val tasks = (1..10).map { index ->
            Task(
                description = "Test task: $index"
            )
        }

        return Result.Success(tasks)
    }
}