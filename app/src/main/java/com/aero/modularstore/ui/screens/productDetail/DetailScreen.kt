package com.aero.modularstore.ui.screens.productDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aero.modularstore.navigation.NavigationCallbacks
import com.aero.modularstore.repository.CartRepository
import com.aero.modularstore.ui.screens.productDetail.components.AddToCartButton
import com.aero.modularstore.ui.screens.productDetail.components.ProductDescriptionSection
import com.aero.modularstore.ui.screens.productDetail.components.ProductInfoSection
import com.aero.modularstore.ui.screens.productDetail.components.ProductNotFoundSection
import com.aero.modularstore.ui.screens.productDetail.components.PriceSection

@Composable
fun DetailScreen(
    productId: Int,
    navigationCallbacks: NavigationCallbacks,
    detailViewModel: DetailViewModel,
    onFavoriteToggle: (Int) -> Unit
) {
    val uiState by detailViewModel.uiState.collectAsState()
    val product = uiState.getProduct(productId)

    if (product == null) {
        ProductNotFoundSection()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ProductInfoSection(product)
            Spacer(modifier = Modifier.height(16.dp))

            ProductDescriptionSection(product.description)
            Spacer(modifier = Modifier.height(24.dp))

            PriceSection(product.price, product.id)
            Spacer(modifier = Modifier.height(32.dp))

            AddToCartButton(
                onClick = {
                    CartRepository.addToCart(product)
                    navigationCallbacks.navigateToCart()
                }
            )
        }
    }
}