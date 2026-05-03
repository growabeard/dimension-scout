package com.witt.dimensionscout.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.witt.dimensionscout.presentation.characters.CharacterDetailScreen
import com.witt.dimensionscout.presentation.characters.CharacterSearchScreen
import com.witt.dimensionscout.presentation.characters.CharacterSearchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: Any = CharacterGrid
) {
    val navController = rememberNavController()
    val viewModel: CharacterSearchViewModel = koinViewModel()
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<CharacterGrid> {

            CharacterSearchScreen(
                uiState = uiState,
                showClearButton = viewModel.showClearButton,
                onQueryChange = viewModel::onQueryChange,
                onSearch = viewModel::onSearch,
                onClearInputClick = viewModel::clearInput,
                onLoadNextPage = viewModel::loadNextPage,
                onCharacterClick = { index ->
                    val characterId = viewModel.onCharacterClick(index)
                    navController.navigate(CharacterDetail(characterId))
                },
                modifier = modifier
            )
        }

        composable<CharacterDetail> { backStackEntry ->
            val detailRoute: CharacterDetail = backStackEntry.toRoute()

            val character = uiState.characters.find { it.id == detailRoute.itemId }

            if (character != null) {
                CharacterDetailScreen(
                    character = character,
                    onCloseButtonClick = {
                        navController.popBackStack()
                    }
                )
            } else {
                navController.popBackStack()
            }
        }
    }
}