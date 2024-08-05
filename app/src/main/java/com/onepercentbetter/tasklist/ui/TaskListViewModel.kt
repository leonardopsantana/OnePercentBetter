package com.onepercentbetter.tasklist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onepercentbetter.R
import com.onepercentbetter.core.data.Result
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.core.ui.AlertMessage
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.tasklist.domain.usecases.GetTasksForDateUseCase
import com.onepercentbetter.tasklist.domain.usecases.MarkTaskAsCompleteUseCase
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
    private val markTaskAsCompleteUseCase: MarkTaskAsCompleteUseCase,
    private val rescheduleTaskUseCase: RescheduleTaskUseCase
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
                        completedTasks = null
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

    private fun getViewStateTaskListResults(result: Result<List<Task>>): TaskListViewState {
        return when (result) {
            is Result.Success -> {
                val (complete, incomplete) =
                    result.data.partition { task ->
                        task.completed
                    }

                _viewState.value.copy(
                    incompleteTasks = incomplete,
                    completedTasks = complete,
                    showLoading = false,
                )
            }

            is Result.Error -> {
                _viewState.value.copy(
                    errorMessage = UIText.StringText("Something went wrong."),
                    showLoading = false,
                )
            }
        }
    }

    fun onPreviousDateButtonClicked() {
        _viewState.update {
            it.copy(
                selectedDate = _viewState.value.selectedDate.minusDays(1),
            )
        }
    }

    fun onNextDateButtonClicked() {
        _viewState.update {
            it.copy(
                selectedDate = _viewState.value.selectedDate.plusDays(1),
            )
        }
    }

    fun onDoneButtonClicked(task: Task) {
        viewModelScope.launch {
            markTaskAsCompleteUseCase.invoke(task)
        }
    }

    fun onDateSelected(date: LocalDate) {
        _viewState.update {
            it.copy(
                selectedDate = date,
            )
        }
    }

    fun onRescheduleButtonClicked(task: Task) {
        _viewState.update {
            it.copy(
                taskToReschedule = task,
            )
        }
    }

    fun onTaskRescheduled(
        task: Task,
        newDate: LocalDate
    ) {
        if (newDate < LocalDate.now()) {
            _viewState.update {
                it.copy(
                    taskToReschedule = null,
                    alertMessage = AlertMessage(
                        message = UIText.ResourceText(R.string.err_scheduled_date_in_past)
                    )
                )
            }

            return
        }

        val taskRescheduleAlertMessage = AlertMessage(
            message = UIText.StringText("Task Rescheduled for: $newDate"),
            actionText = UIText.StringText("UNDO"),
            onActionClicked = {
                _viewState.update {
                    val updateTasks = it.incompleteTasks?.plus(task)

                    it.copy(
                        alertMessage = null,
                        incompleteTasks = updateTasks
                    )
                }
            },
            onDismissed = {
                viewModelScope.launch {
                    rescheduleTaskUseCase.invoke(task, newDate)

                    _viewState.update {
                        it.copy(
                            taskToReschedule = null,
                            alertMessage = null
                        )
                    }
                }
            },
            duration = AlertMessage.Duration.LONG
        )

        _viewState.update {
            val tempTasks = it.incompleteTasks?.minus(task)

            it.copy(
                taskToReschedule = null,
                incompleteTasks = tempTasks,
                alertMessage = taskRescheduleAlertMessage
            )
        }
    }

    fun onAlertMessageShown() {
        _viewState.update {
            it.copy(
                alertMessage = null
            )
        }
    }

    fun onReschedulingCompleted() {
        _viewState.update {
            it.copy(
                taskToReschedule = null
            )
        }
    }
}
