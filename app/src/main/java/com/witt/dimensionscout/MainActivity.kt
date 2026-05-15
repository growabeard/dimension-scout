package com.witt.dimensionscout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.witt.dimensionscout.navigation.AppNavHost
import com.witt.dimensionscout.ui.theme.DimensionScoutTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            DimensionScoutTheme {
                AppNavHost()
            }
        }
    }
}
