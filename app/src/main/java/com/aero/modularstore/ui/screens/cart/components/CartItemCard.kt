package com.aero.modularstore.ui.screens.cart.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aero.modularstore.model.CartItem

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onRemove: () -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = cartItem.product.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "$${cartItem.product.price}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                IconButton(onClick = onRemove) {
                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Cantidad: ${cartItem.quantity}")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Button(
                        onClick = { if (cartItem.quantity > 1) onQuantityChange(cartItem.quantity - 1) },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text("-")
                    }
                    Button(
                        onClick = { onQuantityChange(cartItem.quantity + 1) },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text("+")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Subtotal: $${cartItem.subtotal}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

