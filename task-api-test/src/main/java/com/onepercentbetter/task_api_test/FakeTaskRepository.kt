package com.onepercentbetter.task_api_test

import com.onepercentbetter.core.data.Result
import com.onepercentbetter.core_model.Task
import com.onepercentbetter.task_api.TaskListResult
import com.onepercentbetter.task_api.TaskRepository
import kotlinx.coroutines.flow.Flow

typealias TasksForDateInput = Pair<Long, Boolean>

/**
 * A concrete implementation of [TaskRepository] that allows the caller to mock and verify
 * calls to this repo.
 */
class FakeTaskRepository : TaskRepository {

    lateinit var allTasksResult: Flow<TaskListResult>

    val tasksForDateResults: MutableMap<TasksForDateInput, Flow<TaskListResult>> = mutableMapOf()

    val addTasksResults: MutableMap<Task, Result<Unit>> = mutableMapOf()

    val updateTaskResults: MutableMap<Task, Result<Unit>> = mutableMapOf()

    override fun fetchAllTasks(): Flow<TaskListResult> {
        return allTasksResult
    }

    override fun fetchTasksForDate(dateMillis: Long, completed: Boolean): Flow<TaskListResult> {
        val inputPair = Pair(dateMillis, completed)

        return tasksForDateResults[inputPair]!!
    }

    override suspend fun addTask(task: Task): Result<Unit> {
        return addTasksResults[task]!!
    }

    override suspend fun deleteTask(task: Task): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: Task): Result<Unit> {
        return updateTaskResults[task]!!
    }
}
