package com.aero.modularstore.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aero.modularstore.ui.theme.AppThemeMode

@Composable
fun ThemeSelector(
    onThemeSelected: (AppThemeMode) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        AppButton(
            text = "Blue"
        ) {
            onThemeSelected(AppThemeMode.BLUE)
        }
        AppButton(
            text = "Green"
        ) {
            onThemeSelected(AppThemeMode.GREEN)
        }
        AppButton(
            text = "Purple"
        ) {
            onThemeSelected(AppThemeMode.PURPLE)
        }
        AppButton(
            text = "Black"
        ) {
            onThemeSelected(AppThemeMode.BLACK)
        }
    }
}