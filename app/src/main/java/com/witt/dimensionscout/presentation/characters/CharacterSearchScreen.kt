package com.witt.dimensionscout.presentation.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.witt.dimensionscout.domain.model.Location
import com.witt.dimensionscout.domain.model.Origin
import com.witt.dimensionscout.domain.model.RMCharacter
import com.witt.dimensionscout.ui.theme.DimensionScoutTheme
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun CharacterSearchPreview() {
    DimensionScoutTheme {
        CharacterScreen(
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
            onSearch = {}
        )
    }
}

@Composable
fun CharacterSearchRoute(
    modifier: Modifier = Modifier,
    viewModel: CharacterSearchViewModel = koinViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    CharacterScreen(
        uiState = uiState,
        onSearch = viewModel::getCharacters,
        modifier = modifier
    )
}

@Composable
fun CharacterScreen(
    uiState: CharacterSearchState,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    Column(modifier = modifier) {
        if (uiState.isLoading) {
            Text("Loading")
        } else if (uiState.error != null) {
            Text("Error: ${uiState.error}")
        } else {
            Text("There are ${uiState.characters.size} characters")
        }
    }
}