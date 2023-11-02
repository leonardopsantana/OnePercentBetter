package com.onepercentbetter.tasklist.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.onepercentbetter.R
import com.onepercentbetter.core.ui.theme.OPBTheme
import com.onepercentbetter.tasklist.domain.model.Task

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskListContent(
    viewState: TaskListViewState,
    onRescheduleClicked: (Task) -> Unit,
    onDoneClicked: (Task) -> Unit,
    onAddButtonClicked: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            if (viewState is TaskListViewState.Loaded) {
                AddTaskButton(onAddButtonClicked)
            }
        },
    ) {
        if (viewState is TaskListViewState.Loaded) {
            TaskList(
                tasks = viewState.tasks,
                onRescheduleClicked,
                onDoneClicked
            )
        }
    }
}

@Composable
private fun AddTaskButton(onAddButtonClicked: () -> Unit) {
    FloatingActionButton(
        onClick = onAddButtonClicked,
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = stringResource(R.string.add_task)
        )
    }
}

@Preview(
    name = "Night mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun TaskListContentPreview() {
    val tasks = (1..10).map { index ->
        Task(
            description = "Test task: $index"
        )
    }

    val viewState = TaskListViewState.Loaded(tasks)

    OPBTheme {
        TaskListContent(viewState, {}, {}, {})
    }
}