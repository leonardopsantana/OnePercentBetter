package com.onepercentbetter.tasklist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onepercentbetter.R
import com.onepercentbetter.core.ui.components.OPBTextButton
import com.onepercentbetter.core.ui.theme.OPBTheme
import com.onepercentbetter.tasklist.domain.model.Task

/**
 * This displays a list item for a given [task].
 */
@Composable
fun TaskListItem(
    task: Task,
    onRescheduleClicked: () -> Unit,
    onDoneClicked: () -> Unit,
) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            TaskText(
                text = task.description
            )

            ButtonActionRow(
                onRescheduleClicked = onRescheduleClicked,
                onDoneClicked = onDoneClicked
            )
        }
    }
}

@Composable
private fun ButtonActionRow(
    onRescheduleClicked: () -> Unit,
    onDoneClicked: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Reschedule(onRescheduleClicked)

        Done(onDoneClicked)
    }
}

@Composable
private fun Reschedule(onRescheduleClicked: () -> Unit) {
    OPBTextButton(
        text = stringResource(R.string.reschedule),
        onClick = onRescheduleClicked
    )
}

@Composable
private fun Done(onDoneClicked: () -> Unit) {
    OPBTextButton(
        text = stringResource(R.string.done),
        onClick = onDoneClicked
    )
}

@Composable
private fun TaskText(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
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
private fun TaskListItemPreview() {
    val task = Task(
        description = "Read 30min"
    )

    OPBTheme {
        TaskListItem(
            task = task,
            onRescheduleClicked = {},
            onDoneClicked = {}
        )
    }
}
