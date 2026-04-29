package com.witt.dimensionscout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.witt.dimensionscout.data.repository.GreetingRepositoryImpl
import com.witt.dimensionscout.domain.use_case.GetGreetingUseCase
import com.witt.dimensionscout.presentation.greeting.GreetingScreen
import com.witt.dimensionscout.presentation.greeting.GreetingViewModel
import com.witt.dimensionscout.ui.theme.DimensionScoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val repository = GreetingRepositoryImpl()
        val useCase = GetGreetingUseCase(repository)
        val viewModel = GreetingViewModel(useCase)

        enableEdgeToEdge()
        setContent {
            DimensionScoutTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GreetingScreen(
                        state = viewModel.state.value,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
