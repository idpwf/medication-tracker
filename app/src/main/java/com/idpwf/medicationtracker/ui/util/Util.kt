package com.idpwf.medicationtracker.ui.util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.borderStroke(color: Color): Modifier {
    return this.border(BorderStroke(1.dp, color))
}
