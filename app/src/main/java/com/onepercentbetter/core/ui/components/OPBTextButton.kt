package com.onepercentbetter.core.ui.components

import android.content.res.Configuration
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import com.onepercentbetter.core.ui.theme.OPBTheme

/**
 * This is our custom version of a [TextButton] that ensures that supplied [text] is uppercase
 */
@Composable
fun OPBTextButton(
    text: String,
    onClick: () -> Unit
) {
    TextButton(onClick = { onClick }) {
        Text(
            text = text.toUpperCase(Locale.current)
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
private fun TaskListItemPreview() {
    OPBTheme {
        OPBTextButton(
            text = "OPB Text button",
            onClick = {}
        )
    }
}