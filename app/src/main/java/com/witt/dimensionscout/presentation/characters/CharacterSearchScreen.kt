package com.witt.dimensionscout.presentation.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.witt.dimensionscout.domain.model.Location
import com.witt.dimensionscout.domain.model.Origin
import com.witt.dimensionscout.domain.model.RMCharacter
import com.witt.dimensionscout.presentation.characters.components.CharacterList
import com.witt.dimensionscout.presentation.characters.components.CharacterSearchBar
import com.witt.dimensionscout.presentation.characters.components.EmptyResultsComponent
import com.witt.dimensionscout.presentation.characters.components.ErrorComponent
import com.witt.dimensionscout.ui.theme.DimensionScoutTheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Preview(showSystemUi = true)
@Composable
fun CharacterSearchSearchWithResultsPreview() {
    DimensionScoutTheme {
        CharacterSearchScreen(
            uiState = CharacterSearchState(
                characters = listOf(
                    RMCharacter(
                        name = "Rick Sanchez",
                        id = 1,
                        status = "Alive",
                        species = "Human",
                        type = "",
                        gender = "Male",
                        origin = Origin(name = "Earth", url = ""),
                        location = Location(name = "Earth", url = ""),
                        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                        episode = listOf("https://rickandmortyapi.com/api/episode/1"),
                        url = "https://rickandmortyapi.com/api/character/1",
                        created = "2017-11-04T18:48:46.250Z"
                    ),
                    RMCharacter(
                        name = "Morty Smith",
                        id = 2,
                        status = "Alive",
                        species = "Human",
                        type = "",
                        gender = "Male",
                        origin = Origin(name = "Earth", url = ""),
                        location = Location(name = "Earth", url = ""),
                        image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                        episode = listOf("https://rickandmortyapi.com/api/episode/1"),
                        url = "https://rickandmortyapi.com/api/character/2",
                        created = "2017-11-04T18:50:21.651Z"
                    )
                ),
            ),
            showClearButton = false,
            onClearInputClick = {},
            onSearch = {},
            onCharacterClick = {},
            onQueryChange = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CharacterSearchSearchEmptyPreview() {
    DimensionScoutTheme {
        CharacterSearchScreen(
            uiState = CharacterSearchState(),
            showClearButton = false,
            onClearInputClick = {},
            onSearch = {},
            onCharacterClick = {},
            onQueryChange = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CharacterSearchSearchLoadingPreview() {
    DimensionScoutTheme {
        CharacterSearchScreen(
            uiState = CharacterSearchState(
                query = "Rick",
                isLoading = true,
            ),
            showClearButton = true,
            onClearInputClick = {},
            onSearch = {},
            onCharacterClick = {},
            onQueryChange = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CharacterSearchSearchErrorPreview() {
    DimensionScoutTheme {
        CharacterSearchScreen(
            uiState = CharacterSearchState(
                query = "Rick",
                error = "Fake error",
            ),
            showClearButton = true,
            onClearInputClick = {},
            onSearch = {},
            onCharacterClick = {},
            onQueryChange = {}
        )
    }
}

@Composable
fun CharacterSearchRoute(
    modifier: Modifier = Modifier,
    viewModel: CharacterSearchViewModel = koinViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CharacterGrid
    ) {
        composable<CharacterGrid> {
            CharacterSearchScreen(
                uiState = uiState,
                showClearButton = viewModel.showClearButton,
                onQueryChange = viewModel::onQueryChange,
                onSearch = viewModel::getCharacters,
                onClearInputClick = viewModel::clearInput,
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
                CharacterDetailScreen(character = character, onCloseButtonClick = {
                    navController.popBackStack()
                })
            } else {
                // Handle error or loading state
            }
        }
    }
}

@Composable
fun CharacterSearchScreen(
    modifier: Modifier = Modifier,
    uiState: CharacterSearchState,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onCharacterClick: (Int) -> Unit,
    showClearButton: Boolean = false,
    onClearInputClick: () -> Unit
) {

    LaunchedEffect(uiState.query) {
        if (!uiState.hasSearched) {
            onSearch()
        }
    }

    Column(modifier = modifier) {
        CharacterSearchBar(
            uiState.query,
            onQueryChange,
            uiState.isLoading,
            showClearButton,
            onClearInputClick,
            onSearch
        )

        when {
            uiState.error != null -> {
                ErrorComponent(message = uiState.error)
            }

            uiState.characters.isNotEmpty() -> {
                CharacterList(uiState.characters, onCharacterClick = onCharacterClick)
            }

            !uiState.isLoading && uiState.hasSearched -> {
                EmptyResultsComponent()
            }
        }
    }
}

@Serializable
object CharacterGrid

@Serializable
data class CharacterDetail(val itemId: Int)