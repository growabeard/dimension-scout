package com.witt.dimensionscout.presentation.greeting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.witt.dimensionscout.ui.theme.DimensionScoutTheme

@Composable
fun GreetingScreen(
    state: GreetingState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            Text(
                text = state.message,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    DimensionScoutTheme {
        Surface {
            GreetingScreen(
                state = GreetingState(
                    message = "Hello from Preview!",
                    isLoading = false
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingScreenLoadingPreview() {
    DimensionScoutTheme {
        Surface {
            GreetingScreen(
                state = GreetingState(
                    isLoading = true
                )
            )
        }
    }
}
