package com.onepercentbetter.core.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.onepercentbetter.core.ui.theme.ButtonShape
import com.onepercentbetter.core.ui.theme.OPBTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * A custom composable that when clicked, launches a date picker.
 */
@Composable
fun OPBDatePicker(
    value: LocalDate,
    onValueChanged: (LocalDate) -> Unit,
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
        backgroundColor = MaterialTheme.colorScheme.surface
    ) {
        this.datepicker(
            colors = md3DatePickerColors(),
            onDateChange = onValueChanged,
            initialDate = value
        )
    }

    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = ButtonShape
            )
            .clip(ButtonShape)
            .clickable {
                dialogState.show()
            }
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = value.toUIString(),
                color = textColor,
                modifier = Modifier.weight(1F)
            )

            Icon(
                Icons.Default.DateRange,
                contentDescription = "Select date",
                tint = iconColor
            )
        }
    }
}

private fun LocalDate.toUIString(): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy")
    return formatter.format(this)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun md3DatePickerColors(
    headerBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    headerTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    calendarHeaderTextColor: Color = MaterialTheme.colorScheme.onBackground,
    activeBackgroundColor: Color = MaterialTheme.colorScheme.primary,
    inactiveBackgroundColor: Color = Color.Transparent,
    activeTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    inactiveTextColor: Color = MaterialTheme.colorScheme.onBackground,
): com.vanpra.composematerialdialogs.datetime.date.DatePickerColors {
    return DatePickerDefaults.colors(
        headerBackgroundColor,
        headerTextColor,
        calendarHeaderTextColor,
        activeBackgroundColor,
        inactiveBackgroundColor,
        activeTextColor,
        inactiveTextColor
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
private fun OPBDatePickerPreview() {
    OPBTheme {
        Surface {
            OPBDatePicker(
                value = LocalDate.now(),
                onValueChanged = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}