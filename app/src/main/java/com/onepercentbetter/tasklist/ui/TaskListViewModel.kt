package com.onepercentbetter.tasklist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onepercentbetter.core.data.Result
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.tasklist.domain.usecases.GetAllTasksUseCase
import com.onepercentbetter.tasklist.domain.usecases.ProdGetAllAllTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This class is responsible for managing and exposing the UI state of the task list screen.
 */
@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase
) : ViewModel() {
    private val _viewState: MutableStateFlow<TaskListViewState> = MutableStateFlow(TaskListViewState.Loading)
    val viewState: StateFlow<TaskListViewState> = _viewState

    init {
        viewModelScope.launch {
            val getTasksResult = getAllTasksUseCase()

            _viewState.value = when (getTasksResult) {
                is Result.Error -> {
                    TaskListViewState.Error(
                        errorMessage = UIText.StringText("Something went wrong! ;(")
                    )
                }
                is Result.Success -> {
                    TaskListViewState.Loaded(
                        tasks = getTasksResult.data
                    )
                }
            }
        }
    }
}
