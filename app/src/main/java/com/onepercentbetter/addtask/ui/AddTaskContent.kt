package com.onepercentbetter.addtask.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.onepercentbetter.R
import com.onepercentbetter.addtask.domain.model.TaskInput
import com.onepercentbetter.core.ui.components.OPBTextField
import com.onepercentbetter.core.ui.components.PrimaryButton
import com.onepercentbetter.core.ui.components.UIText
import com.onepercentbetter.core.ui.components.VerticalSpacer
import com.onepercentbetter.core.ui.theme.OPBTheme
import java.time.LocalDate

@Composable
fun AddTaskContent(
    viewState: AddTaskViewState,
    onTaskDescriptionChanged: (String) -> Unit,
    onTaskScheduleDateChanged: (LocalDate) -> Unit,
    onSubmitClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.form_spacing))
    ) {
        TaskDescriptionLabel()
        TaskDescriptionInput(
            text = viewState.taskInput.description,
            onTextChanged = onTaskDescriptionChanged
        )
        TaskDateLabel()
        TaskDateInput()
        VerticalSpacer(height = dimensionResource(id = R.dimen.form_spacing))
        SubmitButton(onSubmitClicked)
    }

}

@Composable
private fun SubmitButton(onSubmitClicked: () -> Unit) {
    PrimaryButton(
        text = "Submit",
        onClick = { onSubmitClicked }
    )
}

@Composable
private fun TaskDateInput() {
    OPBTextField(
        text = "Today",
        onTextChanged = {},
        labelText = ""
    )
}

@Composable
private fun TaskDateLabel() {
    Text(
        text = "When would you like to do it?",
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun TaskDescriptionInput(
    text: String,
    onTextChanged: (String) -> Unit
) {
    OPBTextField(
        text = text,
        onTextChanged = onTextChanged,
        labelText = ""
    )
}

@Composable
private fun TaskDescriptionLabel() {
    Text(
        text = "What would you like to accomplish?",
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center
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
private fun AddTaskContentPreview(
    @PreviewParameter(AddTaskViewStateProvider::class)
    addTaskViewState: AddTaskViewState
) {
    OPBTheme {
        Surface {
            AddTaskContent(
                viewState = addTaskViewState,
                onTaskDescriptionChanged = {},
                onTaskScheduleDateChanged = {},
                onSubmitClicked = {}
            )
        }

    }
}

class AddTaskViewStateProvider : PreviewParameterProvider<AddTaskViewState> {
    override val values: Sequence<AddTaskViewState>
        get() {
            val activeInput = TaskInput(
                description = "Clean the office",
                scheduledDate = LocalDate.now()
            )

            return sequenceOf(
                AddTaskViewState.Initial,
                AddTaskViewState.Active(
                    taskInput = activeInput
                ),
                AddTaskViewState.Submitting(
                    taskInput = activeInput
                ),
                AddTaskViewState.SubmissionError(
                    taskInput = activeInput,
                    errorMessage = UIText.StringText("Whoops")
                ),
                AddTaskViewState.Completed
            )
        }

}