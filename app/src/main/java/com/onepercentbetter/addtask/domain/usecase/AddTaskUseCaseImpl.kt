package com.onepercentbetter.addtask.domain.usecase

import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.core.datastore.UserPreferences
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.repository.task.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class AddTaskUseCaseImpl @Inject
    constructor(
        private val taskRepository: TaskRepository,
        private val userPreferences: UserPreferences,
    ) : AddTaskUseCase {
        @Suppress("ReturnCount")
        override suspend fun invoke(
            task: Task,
            ignoreTaskLimits: Boolean,
        ): Flow<AddTaskResult> {
            val sanitizedTask = task.copy(
                description = task.description.trim(),
            )

            val validationResult = validateInput(sanitizedTask)

            if (validationResult != null) {
                return flowOf(validationResult)
            }

            if (!ignoreTaskLimits) {
                val preferenceCheckResult = ensureNumTasksWithinPreferences(task, taskRepository, userPreferences)

                if (preferenceCheckResult != null) {
                    return flowOf(preferenceCheckResult)
                }
            }

            val result = taskRepository.addTask(sanitizedTask)

            return flowOf(
                result.fold(
                    onSuccess = {
                        AddTaskResult.Success
                    },
                    onFailure = {
                        AddTaskResult.Failure.Unknown
                    },
                ),
            )
        }

        private suspend fun ensureNumTasksWithinPreferences(
            task: Task,
            taskRepository: TaskRepository,
            userPreferences: UserPreferences,
        ): AddTaskResult.Failure.MaxTasksPerDayExceeded? {
            if (!userPreferences.getPreferredNumTasksPerDayEnabled()) {
                return null
            }

            val preferredNumTasks = userPreferences.getPreferredNumTasksPerDay() ?: return null

            val incompleteTaskList = taskRepository
                .fetchTasksForDate(
                    dateMillis = task.scheduledDateMillis,
                    completed = false,
                ).first()
                .getOrNull()

            val numIncompleteTasks = incompleteTaskList?.size ?: 0

            return if (numIncompleteTasks >= preferredNumTasks) {
                AddTaskResult.Failure.MaxTasksPerDayExceeded
            } else {
                null
            }
        }

        /**
         * Since it's no longer possible to select a date in the past (our date picker validates this),
         * we can simplify this to only validate that the description is not empty.
         */
        private fun validateInput(
            task: Task,
        ): AddTaskResult.Failure.InvalidInput? {
            val emptyDescription = task.description.isBlank()

            val scheduledDate = Instant
                .ofEpochMilli(task.scheduledDateMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            val scheduledDateInPast = scheduledDate.isBefore(LocalDate.now())

            return if (emptyDescription || scheduledDateInPast) {
                AddTaskResult.Failure.InvalidInput(
                    emptyDescription = emptyDescription,
                    scheduledDateInPast = scheduledDateInPast,
                )
            } else {
                null
            }
        }
    }
