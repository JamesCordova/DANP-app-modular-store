package com.aero.modularstore.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aero.modularstore.ui.components.AppBottomNavigationBar
import com.aero.modularstore.ui.components.NavScreens
import com.aero.modularstore.ui.components.AppToolbar
import com.aero.modularstore.ui.screens.cart.CartScreen
import com.aero.modularstore.ui.screens.cart.CartViewModel
import com.aero.modularstore.ui.screens.cart.CartViewModelFactory
import com.aero.modularstore.ui.screens.favorites.FavoritesScreen
import com.aero.modularstore.ui.screens.favorites.FavoritesViewModel
import com.aero.modularstore.ui.screens.favorites.FavoritesViewModelFactory
import com.aero.modularstore.ui.screens.home.HomeScreen
import com.aero.modularstore.ui.screens.home.HomeViewModel
import com.aero.modularstore.ui.screens.home.HomeViewModelFactory
import com.aero.modularstore.ui.screens.productDetail.DetailScreen
import com.aero.modularstore.ui.screens.productDetail.DetailViewModel
import com.aero.modularstore.ui.screens.productDetail.DetailViewModelFactory
import com.aero.modularstore.ui.screens.productDetail.components.DetailHeader

data class NavigationCallbacks(
    val navigateToDetail: (productId: Int) -> Unit,
    val navigateBack: () -> Unit,
    val navigateToHome: () -> Unit,
    val navigateToFavorites: () -> Unit,
    val navigateToCart: () -> Unit
)

@Composable
fun AppNavigation(
    onThemeChange: (String) -> Unit
) {
    val navController = rememberNavController()
    val homeViewModelFactory = HomeViewModelFactory()
    val homeViewModel: HomeViewModel = viewModel(factory = homeViewModelFactory)
    val homeUiState by homeViewModel.uiState.collectAsState()

    var showCheckoutSuccessDialog by remember { mutableStateOf(false) }

    // Derive current route from navController back stack to stay in sync with navigation
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBefore("/{") ?: NavScreens.HOME.route

    // Get productId from back stack arguments for detail screen
    val productId = navBackStackEntry?.arguments?.getString("productId")?.toIntOrNull() ?: 0

    // Define navigation callbacks
    val navigationCallbacks = NavigationCallbacks(
        navigateToDetail = { id -> navController.navigate("detail/$id") },
        navigateBack = { navController.popBackStack() },
        navigateToHome = {
            navController.navigate(NavScreens.HOME.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        },
        navigateToFavorites = {
            navController.navigate(NavScreens.FAVORITES.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        },
        navigateToCart = {
            navController.navigate(NavScreens.CART.route) {
                launchSingleTop = true
            }
        }
    )

    if (showCheckoutSuccessDialog) {
        CheckoutSuccessDialog(
            onDismiss = {
                showCheckoutSuccessDialog = false
                navigationCallbacks.navigateToHome()
            }
        )
    }

    Scaffold(
        topBar = {
            when (currentRoute) {
                NavScreens.HOME.route -> AppToolbar(title = NavScreens.HOME.label)
                NavScreens.FAVORITES.route -> AppToolbar(title = NavScreens.FAVORITES.label)
                NavScreens.CART.route -> AppToolbar(title = NavScreens.CART.label)
                "detail" -> {
                    val isFavorite = homeUiState.favoriteProductIds.contains(productId)
                    DetailHeader(
                        isFavorite = isFavorite,
                        onBack = navigationCallbacks.navigateBack,
                        onFavoriteToggle = { homeViewModel.toggleFavorite(productId) }
                    )
                }
                else -> {}
            }
        },
        bottomBar = {
            // No mostrar BottomBar en DetailScreen ni CartScreen
            if (!isDetailScreen(currentRoute) && currentRoute != NavScreens.CART.route) {
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
                    navigationCallbacks = navigationCallbacks,
                    onThemeChange = onThemeChange,
                    homeViewModel = homeViewModel
                )
            }
            composable(NavScreens.FAVORITES.route) {
                val favoritesViewModelFactory = FavoritesViewModelFactory(homeUiState.favoriteProductIds)
                val favoritesViewModel: FavoritesViewModel = viewModel(
                    factory = favoritesViewModelFactory
                )

                FavoritesScreen(
                    navigationCallbacks = navigationCallbacks,
                    favoritesViewModel = favoritesViewModel,
                    onFavoriteToggle = { productId -> homeViewModel.toggleFavorite(productId) }
                )
            }
            composable(
                "detail/{productId}"
            ) { backStack ->
                val productId =
                    backStack.arguments?.getString("productId")?.toIntOrNull() ?: 0
                val detailViewModelFactory = DetailViewModelFactory(homeUiState.favoriteProductIds)
                val detailViewModel: DetailViewModel = viewModel(
                    factory = detailViewModelFactory
                )

                DetailScreen(
                    productId = productId,
                    navigationCallbacks = navigationCallbacks,
                    detailViewModel = detailViewModel,
                    onFavoriteToggle = { productId -> homeViewModel.toggleFavorite(productId) }
                )
            }
            composable(NavScreens.CART.route) {
                val cartViewModelFactory = CartViewModelFactory()
                val cartViewModel: CartViewModel = viewModel(
                    factory = cartViewModelFactory
                )

                CartScreen(
                    navigationCallbacks = navigationCallbacks,
                    cartViewModel = cartViewModel,
                    onCheckoutSuccess = {
                        showCheckoutSuccessDialog = true
                    }
                )
            }
        }
    }
}

private fun isDetailScreen(route: String?): Boolean {
    return route == "detail" || route?.startsWith("detail/") == true
}

@Composable
private fun CheckoutSuccessDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("¡Compra exitosa!")
        },
        text = {
            Text("Gracias por tu compra. Tu pedido ha sido registrado correctamente.")
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Aceptar")
            }
        }
    )
}


