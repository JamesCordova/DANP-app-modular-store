package com.aero.modularstore.ui.screens.home

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aero.modularstore.ui.screens.home.components.CategoryFilterButtons
import com.aero.modularstore.ui.screens.home.components.ProductCard
import com.aero.modularstore.ui.screens.home.components.ThemeSelector

@Composable
fun HomeScreen(
    navController: NavController,
    onThemeChange: (String) -> Unit,
    homeViewModel: HomeViewModel = viewModel()
) {
    val uiState by homeViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        ThemeSelector {
            onThemeChange(it.name)
        }
        Spacer(modifier = Modifier.height(12.dp))
        CategoryFilterButtons(
            currentFilter = uiState.selectedCategory,
            onFilterChange = homeViewModel::onCategorySelected
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn {
            items(
                items = uiState.filteredProducts,
                key = { it.id }
            ) { product ->
                ProductCard(
                    product = product,
                    onViewDetail = {
                        navController.navigate(
                            "detail/${it.id}"
                        )
                    },
                    isFavorite = homeViewModel.isFavorite(product.id),
                    onFavoriteToggle = { productId ->
                        homeViewModel.toggleFavorite(productId)
                    }
                )
            }
        }
    }
}