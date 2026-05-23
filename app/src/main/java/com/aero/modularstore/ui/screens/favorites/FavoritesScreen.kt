package com.aero.modularstore.ui.screens.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aero.modularstore.ui.components.AppToolbar
import com.aero.modularstore.ui.screens.favorites.components.EmptyFavoritesSection
import com.aero.modularstore.ui.screens.home.HomeViewModel
import com.aero.modularstore.ui.screens.home.components.ProductCard

@Composable
fun FavoritesScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    val uiState by homeViewModel.uiState.collectAsState()

    val favoriteProducts = uiState.products.filter { product ->
        uiState.favoriteProductIds.contains(product.id)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppToolbar(
            title = "Mis Favoritos"
        )

        if (favoriteProducts.isEmpty()) {
            EmptyFavoritesSection()
        } else {
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn {
                items(
                    items = favoriteProducts,
                    key = { it.id }
                ) { product ->
                    ProductCard(
                        product = product,
                        onViewDetail = {
                            navController.navigate(
                                "detail/${it.id}"
                            )
                        },
                        isFavorite = uiState.favoriteProductIds.contains(product.id),
                        onFavoriteToggle = { productId ->
                            homeViewModel.toggleFavorite(productId)
                        }
                    )
                }
            }
        }
    }
}

