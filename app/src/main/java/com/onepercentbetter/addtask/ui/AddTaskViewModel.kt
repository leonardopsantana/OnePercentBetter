package com.onepercentbetter.addtask.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onepercentbetter.R
import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.addtask.domain.model.TaskInput
import com.onepercentbetter.addtask.domain.usecase.AddTaskUseCase
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.destinations.AddTaskScreenDestination
import com.onepercentbetter.toEpochMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * Even though this screen can be navigated to using either AddTaskDialogDestination, or
     * AddTaskScreenDestination, because they both have same typesafe nav arguments delegate of
     * [AddTaskNavArguments], it does not matter what we use here to call 'argsFrom(savedStateHandle)'
     * because both will have the same functionality.
     */
    private val args = AddTaskScreenDestination.argsFrom(savedStateHandle)

    private val _viewState: MutableStateFlow<AddTaskViewState> = MutableStateFlow(
        AddTaskViewState.Initial(
            initDate = args.initDate
        )
    )
    val viewState = _viewState.asStateFlow()

    fun onTaskDescriptionChanged(newDescription: String) {
        val currentInput = _viewState.value.taskInput
        val newInput =
            currentInput.copy(
                description = newDescription,
            )
        _viewState.value =
            AddTaskViewState.Active(
                taskInput = newInput,
                descriptionInputErrorMessage = null,
                scheduledDateInputErrorMessage =
                (_viewState.value as? AddTaskViewState.Active)
                    ?.scheduledDateInputErrorMessage,
            )
    }

    fun onTaskScheduleDateChanged(newDate: LocalDate) {
        val currentInput = _viewState.value.taskInput
        val newInput =
            currentInput.copy(
                scheduledDate = newDate,
            )

        _viewState.value =
            AddTaskViewState.Active(
                taskInput = newInput,
                descriptionInputErrorMessage =
                (_viewState.value as? AddTaskViewState.Active)
                    ?.descriptionInputErrorMessage,
                scheduledDateInputErrorMessage = null,
            )
    }

    fun onSubmitButtonClicked() {
        val taskToCreate =
            Task(
                id = UUID.randomUUID().toString(),
                description = _viewState.value.taskInput.description,
                scheduledDateMillis =
                _viewState.value.taskInput.scheduledDate
                    .toEpochMillis(),
                    completed = false,
            )

        viewModelScope.launch {
            val result = addTaskUseCase.invoke(taskToCreate)

            _viewState.value =
                when (result) {
                    is AddTaskResult.Success -> {
                        AddTaskViewState.Completed
                    }

                    is AddTaskResult.Failure.InvalidInput -> {
                        result.toViewState(
                            taskInput = _viewState.value.taskInput,
                        )
                    }

                    is AddTaskResult.Failure.Unknown -> {
                        AddTaskViewState.SubmissionError(
                            taskInput = _viewState.value.taskInput,
                            errorMessage = UIText.StringText("Unable to add task"),
                        )
                    }
                }
        }
    }
}

private fun AddTaskResult.Failure.InvalidInput.toViewState(taskInput: TaskInput): AddTaskViewState {
    return AddTaskViewState.Active(
        taskInput = taskInput,
        descriptionInputErrorMessage =
        UIText.ResourceText(R.string.err_empty_task_description)
            .takeIf {
                this.emptyDescription
            },
        scheduledDateInputErrorMessage =
        UIText.ResourceText(R.string.err_scheduled_date_in_past)
            .takeIf {
                this.scheduledDateInPast
            },
    )
}
