package com.onepercentbetter.tasklist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onepercentbetter.R
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.core.ui.AlertMessage
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.repository.task.TaskRepository
import com.onepercentbetter.tasklist.domain.usecases.GetTasksForDateUseCase
import com.onepercentbetter.tasklist.domain.usecases.RescheduleTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

/**
 * This class is responsible for managing and exposing the UI state of the task list screen.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TaskListViewModel
    @Inject
    constructor(
        private val getTasksForDateUseCase: GetTasksForDateUseCase,
        private val rescheduleTaskUseCase: RescheduleTaskUseCase,
        private val taskRepository: TaskRepository,
    ) : ViewModel() {
        private val _viewState: MutableStateFlow<TaskListViewState> =
            MutableStateFlow(TaskListViewState())
        val viewState = _viewState.asStateFlow()

        init {
            _viewState
                .map { viewState ->
                    viewState.selectedDate
                }
                .distinctUntilChanged()
                .flatMapLatest { selectedDate ->
                    _viewState.update {
                        it.copy(
                            showLoading = true,
                            incompleteTasks = null,
                            completedTasks = null,
                        )
                    }

                    getTasksForDateUseCase.invoke(
                        date = selectedDate,
                    )
                }
                .onEach { result ->
                    _viewState.update {
                        getViewStateTaskListResults(result)
                    }
                }
                .launchIn(viewModelScope)
        }

        private fun getViewStateTaskListResults(
            result: Result<List<Task>>,
        ): TaskListViewState {
            return result.fold(
                onSuccess = { tasks ->
                    val (complete, incomplete) =
                        tasks.partition { task ->
                            task.completed
                        }

                    _viewState.value.copy(
                        incompleteTasks = incomplete,
                        completedTasks = complete,
                        showLoading = false,
                    )
                },
                onFailure = {
                    _viewState.value.copy(
                        errorMessage = UIText.StringText("Something went wrong."),
                        showLoading = false,
                    )
                },
            )
        }

        fun onDoneButtonClicked(
            task: Task,
        ) {
            markTaskAsComplete(task)
            val taskAccomplishedAlertMessage = getUndoAlertMessageForTask(task)
            addAlertMessageToState(taskAccomplishedAlertMessage)
        }

        private fun addAlertMessageToState(
            taskAccomplishedAlertMessage: AlertMessage,
        ) {
            _viewState.update { currentState ->
                currentState.copy(
                    alertMessages = currentState.alertMessages + taskAccomplishedAlertMessage,
                )
            }
        }

        private fun markTaskAsComplete(
            task: Task,
        ) {
            viewModelScope.launch {
                val taskAsComplete = task.copy(
                    completed = true,
                )

                taskRepository.updateTask(taskAsComplete)
            }
        }

        private fun getUndoAlertMessageForTask(
            task: Task,
        ) = AlertMessage(
            message = UIText.ResourceText(R.string.task_accomplished, listOf(task.description)),
            actionText = UIText.ResourceText(R.string.undo),
            onActionClicked = {
                val taskAsIncomplete = task.copy(
                    completed = false,
                )

                viewModelScope.launch {
                    taskRepository.updateTask(taskAsIncomplete)
                }
            },
            onDismissed = {
                // Do nothing, we're assuming data source was already updated
                // as if the task was complete.
            },
            duration = AlertMessage.Duration.LONG,
        )

        fun onDateSelected(
            date: LocalDate,
        ) {
            _viewState.update {
                it.copy(
                    selectedDate = date,
                )
            }
        }

        fun onRescheduleButtonClicked(
            task: Task,
        ) {
            _viewState.update {
                it.copy(
                    taskToReschedule = task,
                )
            }
        }

        fun onTaskRescheduled(
            task: Task,
            newDate: LocalDate,
        ) {
            if (newDate < LocalDate.now()) {
                _viewState.update {
                    it.copy(
                        taskToReschedule = null,
                        alertMessages = it.alertMessages + AlertMessage(
                            message = UIText.ResourceText(R.string.err_scheduled_date_in_past),
                        ),
                    )
                }

                return
            }

            val taskRescheduledAlertMessage = AlertMessage(
                message = UIText.ResourceText(R.string.task_rescheduled),
                actionText = UIText.ResourceText(R.string.undo),
                onActionClicked = {
                    _viewState.update {
                        val updatedTasks = it.incompleteTasks?.plus(task)

                        it.copy(
                            incompleteTasks = updatedTasks,
                        )
                    }
                },
                onDismissed = {
                    viewModelScope.launch {
                        rescheduleTaskUseCase.invoke(task, newDate)

                        _viewState.update {
                            it.copy(
                                taskToReschedule = null,
                            )
                        }
                    }
                },
                duration = AlertMessage.Duration.SHORT,
            )

            _viewState.update {
                val tempTasks = it.incompleteTasks?.minus(task)

                it.copy(
                    taskToReschedule = null,
                    incompleteTasks = tempTasks,
                    alertMessages = it.alertMessages + taskRescheduledAlertMessage,
                )
            }
        }

        fun onAlertMessageShown(
            shownId: Long,
        ) {
            _viewState.update {
                val newAlertMessages = it.alertMessages.filter { alertMessage ->
                    alertMessage.id != shownId
                }

                it.copy(
                    alertMessages = newAlertMessages,
                )
            }
        }

        fun onReschedulingCompleted() {
            _viewState.update {
                it.copy(
                    taskToReschedule = null,
                )
            }
        }
    }
