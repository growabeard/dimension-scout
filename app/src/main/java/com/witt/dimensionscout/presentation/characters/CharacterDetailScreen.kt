package com.witt.dimensionscout.presentation.characters

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.witt.dimensionscout.R
import com.witt.dimensionscout.domain.model.Character
import com.witt.dimensionscout.ui.theme.DimensionScoutTheme

@Preview(showSystemUi = true)
@Composable
fun CharacterDetailPreview() {
    DimensionScoutTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CharacterDetailScreen(
                    Character(
                        name = "Rick Sanchez",
                        id = 1,
                        status = "Alive",
                        species = "Human",
                        type = "",
                        gender = "Male",
                        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                        episode = listOf("https://rickandmortyapi.com/api/episode/1"),
                        url = "https://rickandmortyapi.com/api/character/1",
                        created = "2017-11-04T18:48:46.250Z",
                        origin = "Earth",
                        displayDate = "November 4, 2017"
                    ),
                    {},
                    this@SharedTransitionLayout,
                    this@AnimatedVisibility
                )
            }
        }
    }
}

@Preview(
    name = "Landscape Mode",
    showBackground = true,
    device = "spec:width=1080dp,height=600dp,dpi=440"
)
@Composable
fun CharacterDetailLandscapePreview() {
    DimensionScoutTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CharacterDetailScreen(
                    Character(
                        name = "Rick Sanchez",
                        id = 1,
                        status = "Alive",
                        species = "Human",
                        type = "",
                        gender = "Male",
                        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                        episode = listOf("https://rickandmortyapi.com/api/episode/1"),
                        url = "https://rickandmortyapi.com/api/character/1",
                        created = "2017-11-04T18:48:46.250Z",
                        origin = "Earth",
                        displayDate = "November 4, 2017"
                    ),
                    {},
                    this@SharedTransitionLayout,
                    this@AnimatedVisibility
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    character: Character,
    onCloseButtonClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onCloseButtonClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.close_content_description)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (isPortrait) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                CharacterImage(
                    character = character,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )

                CharacterDetailsContent(
                    character = character,
                    modifier = Modifier.padding(24.dp)
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                CharacterImage(
                    character = character,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp)
                ) {
                    CharacterDetailsContent(character = character)
                }
            }
        }
    }
}

@Composable
private fun CharacterImage(
    character: Character,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    with(sharedTransitionScope) {
        AsyncImage(
            model = character.image,
            contentDescription = stringResource(
                R.string.image_of_character,
                character.name
            ),
            modifier = modifier.sharedElement(
                rememberSharedContentState(key = "image-${character.id}"),
                animatedVisibilityScope = animatedVisibilityScope
            ),
            placeholder = rememberVectorPainter(Icons.Default.AccountBox),
            error = rememberVectorPainter(Icons.Default.AccountBox),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun CharacterDetailsContent(
    character: Character,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        DetailItem(label = stringResource(R.string.label_species), value = character.species)
        DetailItem(label = stringResource(R.string.label_status), value = character.status)
        DetailItem(label = stringResource(R.string.label_origin), value = character.origin)

        if (character.type.isNotEmpty()) {
            DetailItem(label = stringResource(R.string.label_type), value = character.type)
        }

        DetailItem(label = stringResource(R.string.label_created), value = character.displayDate)

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelMedium.copy(
                letterSpacing = 1.2.sp,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
