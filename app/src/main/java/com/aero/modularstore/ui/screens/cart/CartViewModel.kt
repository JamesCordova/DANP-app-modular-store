package com.aero.modularstore.ui.screens.cart

import androidx.lifecycle.ViewModel
import com.aero.modularstore.model.Cart
import com.aero.modularstore.repository.CartRepository
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {
    val cart: StateFlow<Cart> = CartRepository.cart

    fun removeFromCart(productId: Int) {
        CartRepository.removeFromCart(productId)
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        CartRepository.updateQuantity(productId, quantity)
    }

    fun clearCart() {
        CartRepository.clearCart()
    }

    fun getTotalPrice(): Double {
        return cart.value.totalPrice
    }

    fun getTotalItems(): Int {
        return cart.value.totalItems
    }
}

