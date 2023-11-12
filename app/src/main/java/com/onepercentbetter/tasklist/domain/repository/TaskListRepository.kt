package com.onepercentbetter.tasklist.domain.repository

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.tasklist.domain.model.Task

interface TaskListRepository {
    suspend fun fetchAllTasks(): Result<List<Task>>
}
