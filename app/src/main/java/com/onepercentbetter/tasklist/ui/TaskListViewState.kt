package com.onepercentbetter.tasklist.ui

import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.tasklist.domain.model.Task

sealed class TaskListViewState {
    object Loading : TaskListViewState()

    data class Loaded(
        val tasks: List<Task>
    ) : TaskListViewState()

    data class Error(
        val errorMessage: UIText
    ) : TaskListViewState()
}
