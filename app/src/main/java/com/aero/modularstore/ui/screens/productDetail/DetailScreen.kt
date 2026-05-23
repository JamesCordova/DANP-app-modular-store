package com.aero.modularstore.ui.screens.productDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aero.modularstore.ui.screens.home.HomeViewModel
import com.aero.modularstore.ui.screens.productDetail.components.AddToCartButton
import com.aero.modularstore.ui.screens.productDetail.components.ProductDescriptionSection
import com.aero.modularstore.ui.screens.productDetail.components.ProductInfoSection
import com.aero.modularstore.ui.screens.productDetail.components.ProductNotFoundSection
import com.aero.modularstore.ui.screens.productDetail.components.PriceSection

@Composable
fun DetailScreen(
    productId: Int,
    onBack: () -> Unit,
    homeViewModel: HomeViewModel = viewModel()
) {
    val uiState by homeViewModel.uiState.collectAsState()

    val product = uiState.products.find { it.id == productId }

    if (product == null) {
        ProductNotFoundSection()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        ProductInfoSection(product)
        Spacer(modifier = Modifier.height(16.dp))

        ProductDescriptionSection(product.description)
        Spacer(modifier = Modifier.height(24.dp))

        PriceSection(product.price, product.id)
        Spacer(modifier = Modifier.height(32.dp))

        AddToCartButton(onClick = { })
    }
}