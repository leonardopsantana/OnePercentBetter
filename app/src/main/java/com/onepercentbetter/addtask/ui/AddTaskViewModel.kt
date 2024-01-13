package com.onepercentbetter.addtask.ui

import androidx.lifecycle.ViewModel
import com.onepercentbetter.addtask.domain.usecase.AddTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject
@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {

    private val _viewState: MutableStateFlow<AddTaskViewState> =
        MutableStateFlow(AddTaskViewState.Initial)
    val viewState = _viewState.asStateFlow()

    fun onTaskScheduleDateChanged(newDate: LocalDate) {
        val currentInput = _viewState.value.taskInput
        val newInput = currentInput.copy(
            scheduledDate = newDate
        )

        _viewState.value = AddTaskViewState.Active(
            taskInput = newInput
        )
    }
}
