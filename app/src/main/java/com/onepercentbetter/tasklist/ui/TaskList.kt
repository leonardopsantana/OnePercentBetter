package com.onepercentbetter.tasklist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.onepercentbetter.R
import com.onepercentbetter.core.ui.theme.OPBTheme
import com.onepercentbetter.tasklist.domain.model.Task
import java.time.LocalDate

@Composable
fun TaskList(
    incompleteTasks: List<Task>,
    completedTasks: List<Task>,
    onRescheduleClicked: (Task) -> Unit,
    onDoneClicked: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.list_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.list_padding)),
        modifier = modifier
    ) {
        item {
            Text(
                text = "Incomplete Tasks"
            )
        }
        items(incompleteTasks) { task ->
            TaskListItem(
                task = task,
                onRescheduleClicked = { onRescheduleClicked.invoke(task) },
                onDoneClicked = { onDoneClicked.invoke(task) }
            )
        }
        item {
            Text(
                text = "Completed Tasks"
            )
        }
        items(completedTasks) { task ->
            TaskListItem(
                task = task,
                onRescheduleClicked = { onRescheduleClicked.invoke(task) },
                onDoneClicked = {
                    onDoneClicked.invoke(task)
                }
            )
        }
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
private fun TaskListPreview() {
    val incompleteTasks = (1..5).map { index ->
        Task(
            id = "$index",
            description = "Test task: $index",
            scheduledDate = LocalDate.now(),
            completed = false
        )
    }

    val completedTasks = (1..5).map { index ->
        Task(
            id = "$index",
            description = "Test task: $index",
            scheduledDate = LocalDate.now(),
            completed = true
        )
    }

    OPBTheme {
        TaskList(incompleteTasks, completedTasks, {}, {})
    }
}
