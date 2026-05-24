package com.aero.modularstore.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavoritesViewModelFactory(
    private val favoriteProductIds: List<Int> = emptyList()
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(favoriteProductIds) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
