package com.onepercentbetter.core.ui

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp


/**
 * This is a custom modifier, that will change the width of a Composable, based on the amount of
 * space available to it. Centering the Composable within that available space as well.
 *
 * If the available space is between COMPACT bucket, we will fill the maximum width.
 * If the available space is between MEDIUM bucket, we wil fill 75% of the maximum width.
 * If the available space is between EXPANDED bucket, we wil fill 50% of the maximum width.
 */

private const val COMPACT_WIDTH_RATIO = 1f
private const val MEDIUM_WIDTH_RATIO = 0.75f
private const val EXPANDED_WIDTH_RATIO = 0.5f

private const val MIN_MEDIUM_WIDTH_DP = 600
private const val MIN_EXPANDED_WIDTH_DP = 840

fun Modifier.adaptiveWidth() = this
    .layout { measurable, constraints ->
        val widthRatio = when {
            constraints.maxWidth < MIN_MEDIUM_WIDTH_DP.dp.toPx() -> COMPACT_WIDTH_RATIO
            constraints.maxWidth < MIN_EXPANDED_WIDTH_DP.dp.toPx() -> MEDIUM_WIDTH_RATIO
            else -> EXPANDED_WIDTH_RATIO
        }

        val widthToUse = (constraints.maxWidth * widthRatio).toInt()

        val newConstraints = constraints.copy(
            maxWidth = widthToUse,
            minWidth = widthToUse
        )

        val placeable = measurable.measure(newConstraints)

        val x = Alignment.CenterHorizontally.align(
            size = placeable.width,
            space = constraints.maxWidth,
            layoutDirection = layoutDirection
        )

        layout(width = placeable.width, height = placeable.height) {
            placeable.place(
                x = x,
                y = 0
            )
        }
    }
