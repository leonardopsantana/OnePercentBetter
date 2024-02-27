package com.onepercentbetter.tasklist.domain.repository

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.core_model.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

typealias TaskListResult = Result<List<Task>>

/**
 * This is the data contract for any requests to fetch or modify tasks.
 */
interface TaskRepository {

    /**
     * Request all of the tasks that have been created for the signed in user.
     */
    fun fetchAllTasks(): Flow<TaskListResult>

    /**
     * Request all of the tasks that have been created for the supplied [date].
     */
    fun fetchTasksForDate(date: LocalDate, completed: Boolean): Flow<TaskListResult>

    /**
     * Add new [task] for the signed in user to complete.
     */
    suspend fun addTask(task: Task): Result<Unit>

    /**
     * Delete the supplied [task] from the user's task list.
     */
    suspend fun deleteTask(task: Task): Result<Unit>

    /**
     * Takes the supplied [task] and updates the backing data set for the task with same id.
     */
    suspend fun updateTask(task: Task): Result<Unit>
}
