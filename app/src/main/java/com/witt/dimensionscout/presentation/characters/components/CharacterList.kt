package com.witt.dimensionscout.presentation.characters.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.witt.dimensionscout.R
import com.witt.dimensionscout.domain.model.Character
import com.witt.dimensionscout.ui.theme.DimensionScoutTheme

@Composable
@Preview
fun CharacterListEmptyPreview() {
    DimensionScoutTheme {
        CharacterList(
            characters = emptyList(),
            query = "",
            onCharacterClick = {},
            onLoadNextPage = {},
            paginationErrorId = null,
            isPaginationLoading = false
        )
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
            query = "",
            onCharacterClick = {},
            onLoadNextPage = {},
            paginationErrorId = null,
            isPaginationLoading = false
        )
    }
}

@Composable
@Preview
fun CharacterListPopulatedErrorPreview() {
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
            query = "",
            onCharacterClick = {},
            onLoadNextPage = {},
            paginationErrorId = R.string.error_http_400,
            isPaginationLoading = false
        )
    }
}

@Preview
@Composable
fun CharacterListPopulatedLoadingPreview() {
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
            query = "",
            onCharacterClick = {},
            onLoadNextPage = {},
            paginationErrorId = null,
            isPaginationLoading = true
        )
    }
}


@Composable
fun CharacterList(
    modifier: Modifier = Modifier,
    characters: List<Character>,
    query: String,
    paginationErrorId: Int?,
    isPaginationLoading: Boolean,
    onCharacterClick: (Int) -> Unit,
    onLoadNextPage: () -> Unit
) {
    val listState = rememberLazyGridState()
    var lastScrolledQuery by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(query, characters.isNotEmpty()) {
        if (characters.isNotEmpty() && query != lastScrolledQuery) {
            listState.scrollToItem(0)
            lastScrolledQuery = query
        }
    }

    val shouldLoadMore = remember(characters.size) {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lastVisibleItem.index >= characters.size - 6
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            onLoadNextPage()
        }
    }

    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        state = listState
    ) {
        items(characters.size, key = { index ->
            characters[index].id
        }) { index ->
            CharacterCard(
                character = characters[index],
                onCharacterClick = { onCharacterClick(index) }
            )
        }

        if (paginationErrorId != null) {
            item(span = { GridItemSpan(2) }) {
                PaginationErrorItem(
                    message = stringResource(paginationErrorId),
                    onRetry = onLoadNextPage
                )
            }
        } else if (isPaginationLoading) {
            item(span = { GridItemSpan(2) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }
}

@Composable
fun PaginationErrorItem(
    message: String,
    onRetry: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = onRetry) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Composable
fun CharacterCard(
    character: Character,
    onCharacterClick: () -> Unit
) {
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
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = stringResource(R.string.image_of_character, character.name),
                placeholder = rememberVectorPainter(Icons.Default.AccountCircle),
                error = rememberVectorPainter(Icons.Default.AccountCircle),
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
