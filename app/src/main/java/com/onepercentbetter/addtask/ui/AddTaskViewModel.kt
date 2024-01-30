package com.onepercentbetter.addtask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onepercentbetter.addtask.domain.usecase.AddTaskUseCase
import com.onepercentbetter.core.data.Result
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.tasklist.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {

    private val _viewState: MutableStateFlow<AddTaskViewState> =
        MutableStateFlow(AddTaskViewState.Initial)
    val viewState = _viewState.asStateFlow()

    fun onTaskDescriptionChanged(newDescription: String) {
        val currentInput = _viewState.value.taskInput
        val newInput = currentInput.copy(
            description = newDescription
        )
        _viewState.value = AddTaskViewState.Active(
            taskInput = newInput
        )
    }

    fun onTaskScheduleDateChanged(newDate: LocalDate) {
        val currentInput = _viewState.value.taskInput
        val newInput = currentInput.copy(
            scheduledDate = newDate
        )

        _viewState.value = AddTaskViewState.Active(
            taskInput = newInput
        )
    }

    fun onSubmitButtonClicked() {
        val taskToCreate = Task(
            id = UUID.randomUUID().toString(),
            description = _viewState.value.taskInput.description,
            scheduledDate = _viewState.value.taskInput.scheduledDate
        )

        viewModelScope.launch {
            val result = addTaskUseCase(taskToCreate)

            _viewState.value = when (result) {
                is Result.Success -> {
                    AddTaskViewState.Completed
                }

                is Result.Error -> {
                    AddTaskViewState.SubmissionError(
                        taskInput = _viewState.value.taskInput,
                        errorMessage = UIText.StringText("Unable to add Text")
                    )
                }
            }
        }
    }
}
