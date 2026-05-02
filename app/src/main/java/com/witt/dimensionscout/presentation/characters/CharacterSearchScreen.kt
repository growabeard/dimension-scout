package com.witt.dimensionscout.presentation.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.witt.dimensionscout.R
import com.witt.dimensionscout.domain.model.Character
import com.witt.dimensionscout.presentation.characters.components.CharacterList
import com.witt.dimensionscout.presentation.characters.components.CharacterSearchBar
import com.witt.dimensionscout.presentation.characters.components.EmptyResultsComponent
import com.witt.dimensionscout.presentation.characters.components.ErrorComponent
import com.witt.dimensionscout.ui.theme.DimensionScoutTheme

@Preview(showSystemUi = true)
@Composable
fun CharacterSearchSearchWithResultsPreview() {
    DimensionScoutTheme {
        CharacterSearchScreen(
            uiState = CharacterSearchState(
                characters = listOf(
                    Character(
                        name = "Rick Sanchez",
                        id = 1,
                        status = "Alive",
                        species = "Human",
                        type = "",
                        gender = "Male",
                        origin = "Earth",
                        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                        episode = listOf("https://rickandmortyapi.com/api/episode/1"),
                        url = "https://rickandmortyapi.com/api/character/1",
                        created = "2017-11-04T18:48:46.250Z",
                        displayDate = "November 4, 2017"
                    ),
                    Character(
                        name = "Morty Smith",
                        id = 2,
                        status = "Alive",
                        species = "Human",
                        type = "",
                        gender = "Male",
                        origin = "Earth",
                        image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                        episode = listOf("https://rickandmortyapi.com/api/episode/1"),
                        url = "https://rickandmortyapi.com/api/character/2",
                        created = "2017-11-04T18:50:21.651Z",
                        displayDate = "November 4, 2017"
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
                errorMessageId = R.string.error_generic_exception,
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
fun CharacterSearchScreen(
    modifier: Modifier = Modifier,
    uiState: CharacterSearchState,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onCharacterClick: (Int) -> Unit,
    showClearButton: Boolean = false,
    onClearInputClick: () -> Unit
) {

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
            uiState.errorMessageId != null -> {
                ErrorComponent(message = stringResource( uiState.errorMessageId))
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
