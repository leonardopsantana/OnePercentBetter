package com.onepercentbetter.tasklist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.onepercentbetter.R
import com.onepercentbetter.core.ui.theme.OPBTheme
import com.onepercentbetter.tasklist.domain.model.Task

@Composable
fun TaskList(
    tasks: List<Task>,
    onRescheduleClicked: (Task) -> Unit,
    onDoneClicked: (Task) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.list_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.list_padding))
    ) {
        items(tasks) { task ->
            TaskListItem(
                task = task,
                onRescheduleClicked = { onRescheduleClicked(task) },
                onDoneClicked = { onDoneClicked(task) }
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
    val tasks = (1..10).map { index ->
        Task(
            description = "Test task: $index"
        )
    }

    OPBTheme {
        TaskList(tasks, {}, {})
    }
}
