package com.witt.dimensionscout.navigation

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.witt.dimensionscout.util.CharacterSharer
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import androidx.compose.foundation.layout.fillMaxSize
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.rememberLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.witt.dimensionscout.presentation.characters.UiEvent

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: Any = CharacterGrid
) {
    val navController = rememberNavController()
    val viewModel: CharacterSearchViewModel = koinViewModel()
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = rememberLifecycleOwner()
    val characterSharer: CharacterSharer = koinInject()

    LaunchedEffect(viewModel.eventFlow, lifecycleOwner) {
        viewModel.eventFlow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect { event ->
                when (event) {
                    is UiEvent.ShareCharacter -> {
                        characterSharer.shareCharacter(event.character)
                    }

                    is UiEvent.Idle -> {
                        // this is intentionally empty
                    }
                }
            }
    }

    SharedTransitionLayout(modifier = modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.fillMaxSize()
        ) {
            composable<CharacterGrid> {
                CharacterSearchScreen(
                    uiState = uiState,
                    showClearButton = viewModel.showClearButton,
                    onQueryChange = viewModel::onQueryChange,
                    onSearch = viewModel::onSearch,
                    onClearInputClick = viewModel::clearInput,
                    onLoadNextPage = viewModel::loadNextPage,
                    onCharacterClick = { id ->
                        viewModel.onCharacterClick(id)
                        navController.navigate(CharacterDetail(id))
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                    modifier = modifier
                )
            }

            composable<CharacterDetail> { backStackEntry ->
                val detailRoute: CharacterDetail = backStackEntry.toRoute()
                val character = uiState.characters.find { it.id == detailRoute.itemId }

                if (character != null) {
                    CharacterDetailScreen(
                        character = character,
                        uiState = uiState,
                        onCloseButtonClick = {
                            viewModel.onCharacterDetailClosed()
                            navController.popBackStack()
                        },
                        onShareButtonClick = { char ->
                            viewModel.onCharacterShareClicked(char)
                        },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@composable
                    )
                } else {
                    navController.popBackStack()
                }
            }
        }
    }
}