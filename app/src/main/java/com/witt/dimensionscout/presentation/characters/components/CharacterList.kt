package com.witt.dimensionscout.presentation.characters.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.witt.dimensionscout.domain.model.Location
import com.witt.dimensionscout.domain.model.Origin
import com.witt.dimensionscout.domain.model.RMCharacter
import com.witt.dimensionscout.ui.theme.DimensionScoutTheme

@Composable
@Preview
fun CharacterListEmptyPreview() {
    DimensionScoutTheme {
        CharacterList(emptyList())
    }
}

@Composable
@Preview
fun CharacterListPopulatedPreview() {
    DimensionScoutTheme {
        CharacterList(
            listOf(
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
        )
    }
}

@Composable
fun CharacterList(characters: List<RMCharacter>) {
    Text("There are ${characters.size} characters")
}