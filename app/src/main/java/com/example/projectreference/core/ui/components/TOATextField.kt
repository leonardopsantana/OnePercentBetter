package com.example.projectreference.core.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projectreference.R
import com.example.projectreference.core.ui.theme.TOATheme
import com.example.projectreference.core.ui.theme.TextFieldShape

/**
 * This is a custom implementation of an [OutlineTextField] to ensure that is has the TOA branding and
 * styling as expected.
 *
 * @param[text] The current text inside the input.
 * @param[onTextChanged] A callback invoked whenever the user modifies the text inside this input.
 * @param[labelText] The label that shows above the input when focused
 * @param[modifier] An optional [Modifier] to configure this component
 */
@Composable
fun TOATextField(
    text: String,
    onTextChanged: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
) {

    Column {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChanged,
            label = {
                Text(text = labelText)
            },
            shape = TextFieldShape,
            modifier = modifier
                .heightIn(dimensionResource(id = R.dimen.text_field_height))
                .fillMaxWidth(),
            isError = (errorMessage != null)
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                modifier = Modifier
                    .padding(
                        top = 4.dp,
                        start = 16.dp
                    )
            )
        }
    }
}

@Preview(
    name = "Night mode - Filled",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day mode - Filled",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun FilledTAOTextFieldPreview() {
    TOATheme {
        Surface {
            TOATextField(
                text = "TOA Text Field",
                onTextChanged = {},
                labelText = "Label"
            )
        }
    }
}

@Preview(
    name = "Night mode - Error",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day mode - Error",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun ErrorTAOTextFieldPreview() {
    TOATheme {
        Surface {
            TOATextField(
                text = "TOA Text Field",
                onTextChanged = {},
                labelText = "Label",
                errorMessage = "Please, enter this"
            )
        }
    }
}

@Preview(
    name = "Night mode - Empty",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day mode - Empty",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun EmptyTAOTextFieldPreview() {
    TOATheme {
        Surface {
            TOATextField(
                text = "",
                onTextChanged = {},
                labelText = "Label"
            )
        }
    }
}
