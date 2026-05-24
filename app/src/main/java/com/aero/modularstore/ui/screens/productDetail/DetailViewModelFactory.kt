package com.aero.modularstore.ui.screens.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailViewModelFactory(
    private val favoriteProductIds: List<Int> = emptyList()
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(favoriteProductIds) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

