package com.aero.modularstore.model

data class CartItem(
    val product: Product,
    val quantity: Int
) {
    val subtotal: Double
        get() = product.price * quantity
}

data class Cart(
    val items: List<CartItem> = emptyList()
) {
    val totalPrice: Double
        get() = items.sumOf { it.subtotal }

    val totalItems: Int
        get() = items.sumOf { it.quantity }

    fun addProduct(product: Product, quantity: Int = 1): Cart {
        val existingItem = items.find { it.product.id == product.id }
        return if (existingItem != null) {
            val updatedItems = items.map { item ->
                if (item.product.id == product.id) {
                    item.copy(quantity = item.quantity + quantity)
                } else {
                    item
                }
            }
            copy(items = updatedItems)
        } else {
            copy(items = items + CartItem(product, quantity))
        }
    }

    fun removeProduct(productId: Int): Cart {
        return copy(items = items.filter { it.product.id != productId })
    }

    fun updateQuantity(productId: Int, quantity: Int): Cart {
        return if (quantity <= 0) {
            removeProduct(productId)
        } else {
            val updatedItems = items.map { item ->
                if (item.product.id == productId) {
                    item.copy(quantity = quantity)
                } else {
                    item
                }
            }
            copy(items = updatedItems)
        }
    }

    fun clear(): Cart {
        return Cart(emptyList())
    }
}

