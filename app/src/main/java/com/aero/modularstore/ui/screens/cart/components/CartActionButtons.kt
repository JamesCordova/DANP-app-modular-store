package com.aero.modularstore.ui.screens.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aero.modularstore.navigation.NavigationCallbacks

@Composable
fun CartActionButtons(
    navigationCallbacks: NavigationCallbacks,
    onCheckout: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = { navigationCallbacks.navigateToHome() },
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
        ) {
            Text("Continuar comprando")
        }

        Button(
            onClick = onCheckout,
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
        ) {
            Text("Comprar")
        }
    }
}

@Composable
fun EmptyCartActionButton(
    navigationCallbacks: NavigationCallbacks
) {
    OutlinedButton(
        onClick = { navigationCallbacks.navigateToHome() },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text("Continuar comprando")
    }
}

