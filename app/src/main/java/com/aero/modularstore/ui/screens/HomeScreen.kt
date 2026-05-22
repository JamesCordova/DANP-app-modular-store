package com.aero.modularstore.ui.screens

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
import com.aero.modularstore.ui.components.CategoryFilterButtons
import com.aero.modularstore.ui.components.ProductCard
import com.aero.modularstore.ui.components.ThemeSelector
import com.aero.modularstore.ui.viewmodels.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    navController: NavController,
    onThemeChange: (String) -> Unit
) {
    val homeViewModel: HomeViewModel = viewModel()
    val uiState by homeViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppToolbar(
            title = "Modular Store"
        )
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
                    product = product
                ) {
                    navController.navigate(
                        "detail/${it.name}/${it.price}"
                    )
                }
            }
        }
    }
}