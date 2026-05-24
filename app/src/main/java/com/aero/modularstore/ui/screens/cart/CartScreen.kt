package com.aero.modularstore.ui.screens.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aero.modularstore.navigation.NavigationCallbacks
import com.aero.modularstore.ui.screens.cart.components.CartActionButtons
import com.aero.modularstore.ui.screens.cart.components.CartItemCard
import com.aero.modularstore.ui.screens.cart.components.CartSummary
import com.aero.modularstore.ui.screens.cart.components.EmptyCartActionButton
import com.aero.modularstore.ui.screens.cart.components.EmptyCartSection

@Composable
fun CartScreen(
    navigationCallbacks: NavigationCallbacks,
    cartViewModel: CartViewModel,
    onCheckoutSuccess: () -> Unit
) {
    val cartState by cartViewModel.cart.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (cartState.items.isEmpty()) {
            EmptyCartSection()

            Spacer(modifier = Modifier.height(24.dp))

//            EmptyCartActionButton(navigationCallbacks)
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(
                    items = cartState.items,
                    key = { it.product.id }
                ) { cartItem ->
                    CartItemCard(
                        cartItem = cartItem,
                        onRemove = { cartViewModel.removeFromCart(cartItem.product.id) },
                        onQuantityChange = { newQuantity ->
                            cartViewModel.updateQuantity(cartItem.product.id, newQuantity)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CartSummary(
                totalPrice = cartState.totalPrice,
                totalItems = cartState.totalItems
            )

            Spacer(modifier = Modifier.height(16.dp))

            CartActionButtons(
                navigationCallbacks = navigationCallbacks,
                onCheckout = {
                    onCheckoutSuccess()
                    cartViewModel.clearCart()
                }
            )
        }
    }
}





