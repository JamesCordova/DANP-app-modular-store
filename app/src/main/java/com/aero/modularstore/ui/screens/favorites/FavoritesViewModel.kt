package com.aero.modularstore.ui.screens.favorites

import androidx.lifecycle.ViewModel
import com.aero.modularstore.model.Product
import com.aero.modularstore.model.ProductCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class FavoritesUiState(
    val products: List<Product> = defaultProducts,
    val favoriteProductIds: List<Int> = emptyList()
) {
    val favoriteProducts: List<Product>
        get() = products.filter { it.id in favoriteProductIds }

    companion object {
        private val defaultProducts = listOf(
            Product(
                id = 1,
                name = "Laptop Gamer",
                description = "RTX 4070 + Ryzen 9",
                price = 2500.0,
                category = ProductCategory.COMPUTERS
            ),
            Product(
                id = 2,
                name = "Mechanical Keyboard",
                description = "RGB Switch Blue",
                price = 120.0,
                category = ProductCategory.ACCESSORIES
            ),
            Product(
                id = 3,
                name = "Gaming Mouse",
                description = "16000 DPI",
                price = 75.0,
                category = ProductCategory.ACCESSORIES
            ),
            Product(
                id = 4,
                name = "Iphone 27\"",
                description = "144Hz IPS",
                price = 1220.0,
                category = ProductCategory.COMPUTERS
            )
        )
    }
}

class FavoritesViewModel(
    favoriteProductIds: List<Int> = emptyList()
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        FavoritesUiState(favoriteProductIds = favoriteProductIds)
    )
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    fun updateFavorites(favoriteProductIds: List<Int>) {
        _uiState.value = _uiState.value.copy(favoriteProductIds = favoriteProductIds)
    }

    fun toggleFavorite(productId: Int) {
        val currentFavorites = _uiState.value.favoriteProductIds.toMutableList()
        if (currentFavorites.contains(productId)) {
            currentFavorites.remove(productId)
        } else {
            currentFavorites.add(productId)
        }
        _uiState.value = _uiState.value.copy(favoriteProductIds = currentFavorites)
    }

    fun isFavorite(productId: Int): Boolean {
        return _uiState.value.favoriteProductIds.contains(productId)
    }

    fun getFavoriteCount(): Int {
        return _uiState.value.favoriteProductIds.size
    }
}
