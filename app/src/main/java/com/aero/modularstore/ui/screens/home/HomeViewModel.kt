package com.aero.modularstore.ui.screens.home

import androidx.lifecycle.ViewModel
import com.aero.modularstore.model.Product
import com.aero.modularstore.model.ProductCategory
import com.aero.modularstore.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeUiState(
    val products: List<Product> = defaultProducts,
    val selectedCategory: ProductCategory = ProductCategory.ALL,
    val favoriteProductIds: List<Int> = emptyList(),
    val currentUser: User = User(
        id = 1,
        name = "Usuario Demo",
        email = "usuario@example.com",
        favoriteProductIds = emptyList()
    )
) {
	val filteredProducts: List<Product>
		get() = if (selectedCategory == ProductCategory.ALL) {
			products
		} else {
			products.filter { it.category == selectedCategory }
		}

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

class HomeViewModel : ViewModel() {
	private val _uiState = MutableStateFlow(HomeUiState())
	val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

	fun onCategorySelected(category: ProductCategory) {
		_uiState.value = _uiState.value.copy(selectedCategory = category)
	}

	fun toggleFavorite(productId: Int) {
		val currentFavorites = _uiState.value.favoriteProductIds.toMutableList()
		if (currentFavorites.contains(productId)) {
			currentFavorites.remove(productId)
		} else {
			currentFavorites.add(productId)
		}

		val updatedUser = _uiState.value.currentUser.copy(
			favoriteProductIds = currentFavorites
		)

		_uiState.value = _uiState.value.copy(
			favoriteProductIds = currentFavorites,
			currentUser = updatedUser
		)
	}

	fun isFavorite(productId: Int): Boolean {
		return _uiState.value.favoriteProductIds.contains(productId)
	}

	fun getFavoriteCount(): Int {
		return _uiState.value.favoriteProductIds.size
	}
}