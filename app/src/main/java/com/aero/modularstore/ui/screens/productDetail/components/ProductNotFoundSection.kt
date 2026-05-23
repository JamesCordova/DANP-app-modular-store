package com.aero.modularstore.ui.screens.productDetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductNotFoundSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Producto no encontrado",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

