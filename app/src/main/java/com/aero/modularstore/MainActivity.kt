package com.aero.modularstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aero.modularstore.navigation.AppNavigation
import com.aero.modularstore.ui.theme.AppThemeMode
import com.aero.modularstore.ui.theme.ModularStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            var currentTheme by remember {
                mutableStateOf(AppThemeMode.BLUE)
            }
            ModularStoreTheme(
                themeMode = currentTheme
            ) {
                AppNavigation {
                    currentTheme = when(it) {
                        "GREEN" -> AppThemeMode.GREEN
                        "PURPLE" -> AppThemeMode.PURPLE
                        else -> AppThemeMode.BLUE
                    }
                }
            }
        }
    }
}