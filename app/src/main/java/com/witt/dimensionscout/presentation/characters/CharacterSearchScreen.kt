package com.witt.dimensionscout.presentation.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.witt.dimensionscout.domain.model.Location
import com.witt.dimensionscout.domain.model.Origin
import com.witt.dimensionscout.domain.model.RMCharacter
import com.witt.dimensionscout.presentation.characters.components.CharacterList
import com.witt.dimensionscout.presentation.characters.components.CharacterSearchBar
import com.witt.dimensionscout.presentation.characters.components.EmptyResultsComponent
import com.witt.dimensionscout.presentation.characters.components.ErrorComponent
import com.witt.dimensionscout.ui.theme.DimensionScoutTheme
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
                )
            ),
            showClearButton = false,
            onClearInputClick = {},
            onSearch = {},
            onQueryChange = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CharacterSearchSearchEmptyPreview() {
    DimensionScoutTheme {
        CharacterSearchScreen(
            uiState = CharacterSearchState(
                characters = emptyList()
            ),
            showClearButton = false,
            onClearInputClick = {},
            onSearch = {},
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
                characters = emptyList(),
                isLoading = true,
                query = "Rick"
            ),
            showClearButton = true,
            onClearInputClick = {},
            onSearch = {},
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
                characters = emptyList(),
                error = "Fake error",
                query = "Rick"
            ),
            showClearButton = true,
            onClearInputClick = {},
            onSearch = {},
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

    CharacterSearchScreen(
        uiState = uiState,
        showClearButton = viewModel.showClearButton,
        onQueryChange = viewModel::onQueryChange,
        onSearch = viewModel::getCharacters,
        onClearInputClick = viewModel::clearInput,
        modifier = modifier
    )
}

@Composable
fun CharacterSearchScreen(
    modifier: Modifier = Modifier,
    uiState: CharacterSearchState,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
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
                CharacterList(uiState.characters)
            }

            !uiState.isLoading && uiState.hasSearched -> {
                EmptyResultsComponent()
            }
        }
    }
}