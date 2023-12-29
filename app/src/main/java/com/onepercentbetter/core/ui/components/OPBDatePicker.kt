package com.onepercentbetter.core.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onepercentbetter.core.ui.theme.OPBTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

/**
 * A custom composable that when clicked, launches a date picker.
 */
@Composable
fun OPBDatePicker(
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colorScheme.onSurface.copy(ContentAlpha.disabled),
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    iconColor: Color = MaterialTheme.colorScheme.onSurface.copy(TextFieldDefaults.IconOpacity)
) {
    val dialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("OK")
            negativeButton("CANCEL")
        },
    ) {
        this.datepicker { newDate ->
            //Coming soon
        }
    }

    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(50)
            )
            .clickable {
                dialogState.show()
            }
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Today",
                color = textColor,
                modifier = Modifier.weight(1F)
            )

            Icon(
                Icons.Default.DateRange,
                contentDescription = "Selecte date",
                tint = iconColor
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
private fun OPBDatePickerPreview() {
    OPBTheme {
        Surface {
            OPBDatePicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}
