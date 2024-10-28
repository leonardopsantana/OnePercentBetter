package com.onepercentbetter.addtask.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onepercentbetter.R
import com.onepercentbetter.addtask.domain.model.AddTaskResult
import com.onepercentbetter.addtask.domain.model.TaskInput
import com.onepercentbetter.addtask.domain.usecase.AddTaskUseCase
import com.onepercentbetter.core.di.IoDispatcher
import com.onepercentbetter.core.model.Task
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.destinations.AddTaskScreenDestination
import com.onepercentbetter.core.model.toEpochMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val savedStateHandle: SavedStateHandle,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    /**
     * Even though this screen can be navigated to using either AddTaskDialogDestination, or
     * AddTaskScreenDestination, because they both have same typesafe nav arguments delegate of
     * [AddTaskNavArguments], it does not matter what we use here to call 'argsFrom(savedStateHandle)'
     * because both will have the same functionality.
     */
    private val args = AddTaskScreenDestination.argsFrom(savedStateHandle)

    private val _viewState = MutableStateFlow<AddTaskViewState>(
        AddTaskViewState.Initial(
            initDate = args.initDate,
        ),
    )
    val viewState: StateFlow<AddTaskViewState> = _viewState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = _viewState.value,
    )

    fun onTaskDescriptionChanged(
        newDescription: String,
    ) {
        val currentInput = _viewState.value.taskInput
        val newInput =
            currentInput.copy(
                description = newDescription,
            )
        _viewState.value =
            AddTaskViewState.Active(
                taskInput = newInput,
                descriptionInputErrorMessage = null,
            )
    }

    fun onTaskScheduleDateChanged(
        newDate: LocalDate,
    ) {
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
            )
    }

    fun onSubmitButtonClicked(task: Task) {
        viewModelScope.launch {
            val canRetry = (_viewState.value as? AddTaskViewState.SubmissionError)?.allowRetry

            addTaskUseCase.invoke(
                task = task,
                ignoreTaskLimits = canRetry == true,
            ).flowOn(ioDispatcher).onStart {
                onLoading()
            }
//                .catchFailure {
//                    println(">>>> ${it.errorMessage}")
//                }
                .collect { result ->
                    _viewState.update {
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

                            AddTaskResult.Failure.MaxTasksPerDayExceeded -> {
                                AddTaskViewState.SubmissionError(
                                    taskInput = _viewState.value.taskInput,
                                    errorMessage = UIText.ResourceText(R.string.err_add_task_limit_reached),
                                    overrideButtonText = UIText.ResourceText(R.string.that_is_okay),
                                    allowRetry = true,
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun onLoading() {
        _viewState.update {
            AddTaskViewState.Submitting(
                _viewState.value.taskInput,
            )
        }
    }
}

private fun AddTaskResult.Failure.InvalidInput.toViewState(
    taskInput: TaskInput,
): AddTaskViewState {
    return AddTaskViewState.Active(
        taskInput = taskInput,
        descriptionInputErrorMessage = UIText.ResourceText(R.string.err_empty_task_description)
            .takeIf {
                this.emptyDescription
            },
    )
}
