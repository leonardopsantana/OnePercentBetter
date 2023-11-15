package com.onepercentbetter.tasklist.domain.repository

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.tasklist.domain.model.Task

/**
 * This is the data contract for any requests to fetch or modify tasks.
 */
interface TaskListRepository {

    /**
     * Request all of the tasks that have been created for the signed in user.
     */
    suspend fun fetchAllTasks(): Result<List<Task>>

    /**
     * Add new [task] for the signed in user to complete.
     */
    suspend fun addTask(task: Task): Result<Unit>

    /**
     * Delete the supplied [task] from the user's task list.
     */
    suspend fun deleteTask(task: Task): Result<Unit>

    /**
     * Delete the supplied [task] and marks it as completed.
     */
    suspend fun markAsComplete(task: Task): Result<Unit>
}
