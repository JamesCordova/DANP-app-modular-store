package com.aero.modularstore.ui.screens.productDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aero.modularstore.ui.components.AppToolbar

@Composable
fun DetailScreen(
    name: String,
    price: String
) {
    Column {
        AppToolbar(
            title = "Detalle"
        )
        Column(
            modifier = Modifier.Companion.padding(16.dp)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.Companion.height(12.dp))
            Text(
                text = "Precio: $$price",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}