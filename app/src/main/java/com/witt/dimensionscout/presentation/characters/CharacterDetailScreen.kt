package com.witt.dimensionscout.presentation.characters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.witt.dimensionscout.R
import com.witt.dimensionscout.domain.model.Location
import com.witt.dimensionscout.domain.model.Origin
import com.witt.dimensionscout.domain.model.RMCharacter
import com.witt.dimensionscout.ui.theme.DimensionScoutTheme

@Preview(showSystemUi = true)
@Composable
fun CharacterDetailPreview() {
    DimensionScoutTheme {
        CharacterDetailScreen(
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
            {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(character: RMCharacter, onCloseButtonClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(character.name) },
                actions = {
                    IconButton(onClick = onCloseButtonClick) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = stringResource(R.string.close_content_description)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column {
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                )
                Text(text = "Species: ${character.species}")
                Text(text = "Status: ${character.status}")
                Text(text = "Origin: ${character.origin.name}")
                Text(text = "Type: ${character.type}")
                Text(text = "Created: ${character.getDisplayDate()}")
            }
        }
    }
}