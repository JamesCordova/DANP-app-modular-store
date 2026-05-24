package com.aero.modularstore.repository

import com.aero.modularstore.model.Cart
import com.aero.modularstore.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object CartRepository {
    private val _cart = MutableStateFlow(Cart())
    val cart: StateFlow<Cart> = _cart.asStateFlow()

    fun addToCart(product: Product, quantity: Int = 1) {
        _cart.value = _cart.value.addProduct(product, quantity)
    }

    fun removeFromCart(productId: Int) {
        _cart.value = _cart.value.removeProduct(productId)
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        _cart.value = _cart.value.updateQuantity(productId, quantity)
    }

    fun clearCart() {
        _cart.value = _cart.value.clear()
    }

    fun getCart(): Cart {
        return _cart.value
    }
}

