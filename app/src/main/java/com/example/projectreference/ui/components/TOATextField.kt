package com.example.projectreference.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.projectreference.R
import com.example.projectreference.ui.theme.TOATheme
import com.example.projectreference.ui.theme.TextFieldShape

/**
 * This is a custom implementation of an [OutlineTextField] to ensure that is has the TOA branding and
 * styling as expected
 */
@Composable
fun TOATextField(
    text: String,
    onTextChanged: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
) {

    OutlinedTextField(
        value = text, onValueChange = onTextChanged,
        label = {
            Text(text = labelText)
        },
        shape = TextFieldShape,
        modifier = modifier
            .heightIn(dimensionResource(id = R.dimen.text_field_height))
            .fillMaxWidth()
    )
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
