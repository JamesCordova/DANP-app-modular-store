package com.aero.modularstore.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aero.modularstore.ui.components.AppBottomNavigationBar
import com.aero.modularstore.ui.components.NavScreens
import com.aero.modularstore.ui.components.AppToolbar
import com.aero.modularstore.ui.screens.favorites.FavoritesScreen
import com.aero.modularstore.ui.screens.home.HomeScreen
import com.aero.modularstore.ui.screens.home.HomeViewModel
import com.aero.modularstore.ui.screens.productDetail.DetailScreen
import com.aero.modularstore.ui.screens.productDetail.components.DetailHeader

@Composable
fun AppNavigation(
    onThemeChange: (String) -> Unit
) {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = viewModel()

    // Derive current route from navController back stack to stay in sync with navigation
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: String? = navBackStackEntry?.destination?.route?.substringBefore("/{") ?: NavScreens.HOME.route

    // Get productId from back stack arguments for detail screen
    val productId = navBackStackEntry?.arguments?.getString("productId")?.toIntOrNull() ?: 0

    Scaffold(
        topBar = {
            when (currentRoute) {
                NavScreens.HOME.route -> AppToolbar(title = NavScreens.HOME.label)
                NavScreens.FAVORITES.route -> AppToolbar(title = NavScreens.FAVORITES.label)
                "detail" -> {
                    val isFavorite = homeViewModel.uiState.value.favoriteProductIds.contains(productId)
                    DetailHeader(
                        isFavorite = isFavorite,
                        onBack = { navController.popBackStack() },
                        onFavoriteToggle = { homeViewModel.toggleFavorite(productId) }
                    )
                }
                else -> {}
            }
        },
        bottomBar = {
            // No mostrar BottomBar en DetailScreen
            if (!isDetailScreen(currentRoute)) {
                AppBottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavScreens.HOME.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            composable(NavScreens.HOME.route) {
                HomeScreen(
                    navController,
                    onThemeChange,
                    homeViewModel
                )
            }
            composable(NavScreens.FAVORITES.route) {
                FavoritesScreen(
                    navController,
                    homeViewModel
                )
            }
            composable(
                "detail/{productId}"
            ) { backStack ->
                val productId =
                    backStack.arguments?.getString("productId")?.toIntOrNull() ?: 0
                DetailScreen(
                    productId = productId,
                    onBack = { navController.popBackStack() },
                    homeViewModel = homeViewModel
                )
            }
        }
    }
}

private fun isDetailScreen(route: String?): Boolean {
    return route == "detail" || route?.startsWith("detail/") == true
}
