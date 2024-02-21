package com.onepercentbetter.tasklist.ui

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onepercentbetter.R
import com.onepercentbetter.core.ui.theme.OPBTheme
import com.onepercentbetter.tasklist.domain.model.Task
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
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
        incompleteTasks(incompleteTasks, onRescheduleClicked, onDoneClicked)
        completeTasks(completedTasks, onRescheduleClicked, onDoneClicked)
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.completeTasks(
    completedTasks: List<Task>,
    onRescheduleClicked: (Task) -> Unit,
    onDoneClicked: (Task) -> Unit
) {
    item {
        SectionHeader(
            stringResource(id = R.string.completed_tasks_header)
        )
    }

    if (completedTasks.isEmpty()) {
        item {
            EmptySectionCard(
                text = stringResource(
                    id = R.string.no_complete_tasks_label
                )
            )
        }
    } else {
        items(
            completedTasks,
            key = {
                it.id
            }
        ) { task ->
            TaskListItem(
                task = task,
                onRescheduleClicked = { onRescheduleClicked.invoke(task) },
                onDoneClicked = {
                    onDoneClicked.invoke(task)
                },
                modifier = Modifier
                    .animateItemPlacement()
                    .testTag("COMPLETE_TASK_${task.id}")
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.incompleteTasks(
    incompleteTasks: List<Task>,
    onRescheduleClicked: (Task) -> Unit,
    onDoneClicked: (Task) -> Unit
) {
    item {
        SectionHeader(
            stringResource(id = R.string.incomplete_tasks_header)
        )
    }

    if (incompleteTasks.isEmpty()) {
        item {
            EmptySectionCard(
                stringResource(R.string.no_incomplete_tasks_label)
            )
        }
    } else {
        items(
            incompleteTasks,
            key = {
                it.id
            }
        ) { task ->
            TaskListItem(
                task = task,
                onRescheduleClicked = { onRescheduleClicked.invoke(task) },
                onDoneClicked = { onDoneClicked.invoke(task) },
                modifier = Modifier
                    .animateItemPlacement()
                    .testTag("INCOMPLETE_TASK_${task.id}")
            )
        }
    }
}

@Composable
private fun EmptySectionCard(
    text: String
) {
    Card {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 32.dp, horizontal = 24.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall
    )
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
private fun FullTaskListPreview() {
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

@Preview(
    name = "Night mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)

@Composable
private fun NoIncompleteTaskListPreview() {
    val completedTasks = (1..5).map { index ->
        Task(
            id = "$index",
            description = "Test task: $index",
            scheduledDate = LocalDate.now(),
            completed = true
        )
    }

    OPBTheme {
        TaskList(emptyList(), completedTasks, {}, {})
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
private fun NoCompleteTaskListPreview() {
    val incompleteTasks = (1..5).map { index ->
        Task(
            id = "$index",
            description = "Test task: $index",
            scheduledDate = LocalDate.now(),
            completed = false
        )
    }

    OPBTheme {
        TaskList(incompleteTasks, emptyList(), {}, {})
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
private fun NoTasksListPreview() {
    OPBTheme {
        TaskList(emptyList(), emptyList(), {}, {})
    }
}
