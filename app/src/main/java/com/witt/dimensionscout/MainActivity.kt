package com.witt.dimensionscout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.witt.dimensionscout.presentation.characters.CharacterSearchRoute
import com.witt.dimensionscout.presentation.characters.CharacterSearchViewModel
import com.witt.dimensionscout.ui.theme.DimensionScoutTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CharacterSearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            DimensionScoutTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CharacterSearchRoute(Modifier.padding(innerPadding), viewModel)
                }
            }
        }
    }
}
