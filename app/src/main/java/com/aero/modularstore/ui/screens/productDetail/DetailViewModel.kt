package com.aero.modularstore.ui.screens.productDetail

import androidx.lifecycle.ViewModel
import com.aero.modularstore.model.Product
import com.aero.modularstore.model.ProductCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DetailUiState(
    val products: List<Product> = defaultProducts,
    val favoriteProductIds: List<Int> = emptyList()
) {
    fun getProduct(productId: Int): Product? = products.find { it.id == productId }

    companion object {
        private val defaultProducts = listOf(
            Product(
                id = 1,
                name = "Laptop Gamer",
                description = "RTX 4070 + Ryzen 9",
                price = 2500.0,
                category = ProductCategory.COMPUTERS,
                imageUrl = "https://www.itsitio.com/wp-content/uploads/2020/07/G531-1-scaled-1.jpg"
            ),
            Product(
                id = 2,
                name = "Mechanical Keyboard",
                description = "RGB Switch Blue",
                price = 120.0,
                category = ProductCategory.ACCESSORIES,
                imageUrl = "https://i.insider.com/5fd7cf6b78a5740019a15560?width=1200&format=jpeg"
            ),
            Product(
                id = 3,
                name = "Gaming Mouse",
                description = "16000 DPI",
                price = 75.0,
                category = ProductCategory.ACCESSORIES,
                imageUrl = "https://dlcdnwebimgs.asus.com/gain/1F5AFFA6-D3DC-42CA-B37D-03DAAE123012/w750/h470/fwebp"
            ),
            Product(
                id = 4,
                name = "Iphone 27",
                description = "144Hz IPS",
                price = 1220.0,
                category = ProductCategory.COMPUTERS,
                imageUrl = "https://i.blogs.es/60b358/ios-27/500_333.webp"
            )
        )
    }
}

class DetailViewModel(
    favoriteProductIds: List<Int> = emptyList()
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        DetailUiState(favoriteProductIds = favoriteProductIds)
    )
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

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

    fun getProduct(productId: Int): Product? {
        return _uiState.value.getProduct(productId)
    }
}

