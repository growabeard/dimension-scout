package com.witt.dimensionscout.presentation.characters.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.material.icons.filled.AccountBox
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

val characterList = listOf(
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
    ),
    Character(
        name = "Rick Sanchez",
        id = 3,
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
        id = 4,
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
    ),
    Character(
        name = "Rick Sanchez",
        id = 5,
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
        id = 6,
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
    ),
    Character(
        name = "Rick Sanchez",
        id = 7,
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
        id = 8,
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
)

@Composable
@Preview
fun CharacterListEmptyPreview() {
    DimensionScoutTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CharacterList(
                    characters = emptyList(),
                    query = "",
                    onCharacterClick = {},
                    onLoadNextPage = {},
                    paginationErrorId = null,
                    isPaginationLoading = false,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}

@Composable
@Preview
fun CharacterListPopulatedPreview() {
    DimensionScoutTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CharacterList(
                    characters = characterList,
                    query = "",
                    onCharacterClick = {},
                    onLoadNextPage = {},
                    paginationErrorId = null,
                    isPaginationLoading = false,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}

@Composable
@Preview(
    name = "Landscape Mode",
    showBackground = true,
    device = "spec:width=1080dp,height=600dp,dpi=440"
)
fun CharacterListPopulatedLandscapePreview() {
    DimensionScoutTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CharacterList(
                    characters = characterList,
                    query = "",
                    onCharacterClick = {},
                    onLoadNextPage = {},
                    paginationErrorId = null,
                    isPaginationLoading = false,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}

@Composable
@Preview
fun CharacterListPopulatedErrorPreview() {
    DimensionScoutTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CharacterList(
                    characters = characterList,
                    query = "",
                    onCharacterClick = {},
                    onLoadNextPage = {},
                    paginationErrorId = R.string.error_http_400,
                    isPaginationLoading = false,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}

@Preview
@Composable
fun CharacterListPopulatedLoadingPreview() {
    DimensionScoutTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CharacterList(
                    characters = characterList,
                    query = "",
                    onCharacterClick = {},
                    onLoadNextPage = {},
                    paginationErrorId = null,
                    isPaginationLoading = true,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}


private val MinCharacterCardWidth = 170.dp

@Composable
fun CharacterList(
    modifier: Modifier = Modifier,
    characters: List<Character>,
    onCharacterClick: (Int) -> Unit,
    query: String,
    paginationErrorId: Int?,
    isPaginationLoading: Boolean,
    onLoadNextPage: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
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
        columns = GridCells.Adaptive(MinCharacterCardWidth),
        contentPadding = PaddingValues(8.dp),
        state = listState
    ) {
        items(characters.size, key = { index ->
            characters[index].id
        }) { index ->
            CharacterCard(
                character = characters[index],
                onCharacterClick = { onCharacterClick(index) },
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope
            )
        }

        if (paginationErrorId != null) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                PaginationErrorItem(
                    message = stringResource(paginationErrorId),
                    onRetry = onLoadNextPage
                )
            }
        } else if (isPaginationLoading) {
            item(span = { GridItemSpan(maxLineSpan) }) {
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
    onCharacterClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = { onCharacterClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            with(sharedTransitionScope) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(character.image)
                        .crossfade(true)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = null,
                    placeholder = rememberVectorPainter(Icons.Default.AccountBox),
                    error = rememberVectorPainter(Icons.Default.AccountBox),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .sharedElement(
                            rememberSharedContentState(key = "image-${character.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                    contentScale = ContentScale.Crop
                )
            }

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
