package com.onepercentbetter.tasklist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onepercentbetter.core.data.Result
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.tasklist.domain.model.Task
import com.onepercentbetter.tasklist.domain.usecases.GetTasksForDateUseCase
import com.onepercentbetter.tasklist.domain.usecases.MarkTaskAsCompleteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This class is responsible for managing and exposing the UI state of the task list screen.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val getTasksForDateUseCase: GetTasksForDateUseCase,
    private val markTaskAsCompleteUseCase: MarkTaskAsCompleteUseCase
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
                clearTasksAndShowLoading()

                val incompleteTaskFlow = getTasksForDateUseCase.invoke(
                    date = selectedDate,
                    completed = false,
                )

                val completedTaskFlow = getTasksForDateUseCase.invoke(
                    date = selectedDate,
                    completed = true,
                )

                incompleteTaskFlow.combine(completedTaskFlow) { incompleteTaskResult, completedTaskResult ->
                    (incompleteTaskResult to completedTaskResult)
                }
            }
            .onEach { (incompleteTasksListResult, completedTaskListResult) ->
                _viewState.value = getViewStateTaskListResults(
                    incompleteTasksListResult,
                    completedTaskListResult
                )
            }
            .launchIn(viewModelScope)
    }

    private fun clearTasksAndShowLoading() {
        _viewState.value = _viewState.value.copy(
            showLoading = true,
            incompleteTasks = null
        )
    }

    private fun getViewStateTaskListResults(
        incompleteTasksListResult: Result<List<Task>>,
        completedTaskListResult: Result<List<Task>>
    ): TaskListViewState {
        return when {
            incompleteTasksListResult is Result.Success &&
                completedTaskListResult is Result.Success -> {
                _viewState.value.copy(
                    incompleteTasks = incompleteTasksListResult.data,
                    completedTasks = completedTaskListResult.data,
                    showLoading = false,
                )
            }

            else -> {
                _viewState.value.copy(
                    errorMessage = UIText.StringText("Something went wrong."),
                    showLoading = false,
                )
            }
        }
    }

    fun onPreviousDateButtonClicked() {
        _viewState.value = _viewState.value.copy(
            selectedDate = _viewState.value.selectedDate.minusDays(1)
        )
    }

    fun onNextDateButtonClicked() {
        _viewState.value = _viewState.value.copy(
            selectedDate = _viewState.value.selectedDate.plusDays(1)
        )
    }

    fun onDoneButtonClicked(task: Task) {
        viewModelScope.launch {
            markTaskAsCompleteUseCase.invoke(task)
        }
    }
}
