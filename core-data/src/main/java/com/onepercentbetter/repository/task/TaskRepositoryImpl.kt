package com.onepercentbetter.repository.task

import com.onepercentbetter.core.database.TaskDAO
import com.onepercentbetter.core.model.Task
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl
    @Inject
    constructor(
        private val taskDAO: TaskDAO,
    ) : TaskRepository {
        override fun fetchAllTasks(): Flow<Result<List<Task>>> {
            return taskDAO
                .fetchAllTasks()
                .map { taskList ->
                    Result.success(taskList.toDomainTaskList())
                }
        }

        override fun fetchTasksForDate(
            dateMillis: Long,
            completed: Boolean,
        ): Flow<TaskListResult> {
            val localDate =
                Instant.ofEpochMilli(dateMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()

            return taskDAO
                .fetchTasksForDate(localDate.toPersistableDateString(), completed)
                .map { taskList ->
                    Result.success(taskList.toDomainTaskList())
                }
        }

        override suspend fun addTask(
            task: Task,
        ): Result<Unit> {
            taskDAO.insertTask(task.toPersistableTask())

            return Result.success(Unit)
        }

        override suspend fun deleteTask(
            task: Task,
        ): Result<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun updateTask(
            task: Task,
        ): Result<Unit> {
            taskDAO.updateTask(task.toPersistableTask())

            return Result.success(Unit)
        }
    }
