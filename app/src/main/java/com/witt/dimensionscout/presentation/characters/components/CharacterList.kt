package com.witt.dimensionscout.presentation.characters.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.witt.dimensionscout.R
import com.witt.dimensionscout.domain.model.Character
import com.witt.dimensionscout.ui.theme.DimensionScoutTheme

@Composable
@Preview
fun CharacterListEmptyPreview() {
    DimensionScoutTheme {
        CharacterList(characters = emptyList(), onCharacterClick = {})
    }
}

@Composable
@Preview
fun CharacterListPopulatedPreview() {
    DimensionScoutTheme {
        CharacterList(
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
            onCharacterClick = {}
        )
    }
}

@Composable
fun CharacterList(characters: List<Character>, onCharacterClick: (Int) -> Unit) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(characters.size, key = { index ->
            characters[index].id
        }) { index ->
            CharacterCard(characters[index], onCharacterClick = { onCharacterClick(index) })
        }
    }
}

@Composable
fun CharacterCard(character: Character, onCharacterClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = { onCharacterClick() }
    ) {
        Column {
            AsyncImage(
                model = character.image,
                contentDescription = stringResource(R.string.image_of_character, character.name),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}
