package com.witt.dimensionscout.presentation.characters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.witt.dimensionscout.presentation.characters.components.characterList
import com.witt.dimensionscout.ui.theme.DimensionScoutTheme

@Preview(showSystemUi = true)
@Composable
fun CharacterSearchSearchWithResultsPreview() {
    DimensionScoutTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CharacterSearchScreen(
                    uiState = CharacterSearchState(
                        characters = characterList,
                    ),
                    showClearButton = false,
                    onClearInputClick = {},
                    onSearch = {},
                    onCharacterClick = {},
                    onQueryChange = {},
                    onLoadNextPage = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
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
fun CharacterSearchSearchWithResultsPreviewLandscape() {
    DimensionScoutTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CharacterSearchScreen(
                    uiState = CharacterSearchState(
                        characters = characterList,
                    ),
                    showClearButton = false,
                    onClearInputClick = {},
                    onSearch = {},
                    onCharacterClick = {},
                    onQueryChange = {},
                    onLoadNextPage = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CharacterSearchSearchEmptyPreview() {
    DimensionScoutTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CharacterSearchScreen(
                    uiState = CharacterSearchState(),
                    showClearButton = false,
                    onClearInputClick = {},
                    onSearch = {},
                    onCharacterClick = {},
                    onQueryChange = {},
                    onLoadNextPage = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CharacterSearchSearchLoadingPreview() {
    DimensionScoutTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CharacterSearchScreen(
                    uiState = CharacterSearchState(
                        query = "Rick",
                        isLoading = true,
                    ),
                    showClearButton = true,
                    onClearInputClick = {},
                    onSearch = {},
                    onCharacterClick = {},
                    onQueryChange = {},
                    onLoadNextPage = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CharacterSearchSearchErrorPreview() {
    DimensionScoutTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CharacterSearchScreen(
                    uiState = CharacterSearchState(
                        query = "Rick",
                        errorMessageId = R.string.error_generic_exception,
                    ),
                    showClearButton = true,
                    onClearInputClick = {},
                    onSearch = {},
                    onCharacterClick = {},
                    onQueryChange = {},
                    onLoadNextPage = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
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
fun CharacterSearchSearchErrorLandscapePreview() {
    DimensionScoutTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                CharacterSearchScreen(
                    uiState = CharacterSearchState(
                        query = "Rick",
                        errorMessageId = R.string.error_generic_exception,
                    ),
                    showClearButton = true,
                    onClearInputClick = {},
                    onSearch = {},
                    onCharacterClick = {},
                    onQueryChange = {},
                    onLoadNextPage = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterSearchScreen(
    modifier: Modifier = Modifier,
    uiState: CharacterSearchState,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onLoadNextPage: () -> Unit,
    onCharacterClick: (Int) -> Unit,
    showClearButton: Boolean = false,
    onClearInputClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    Column(modifier = modifier.fillMaxSize()) {
        CharacterSearchBar(
            uiState.query,
            onQueryChange,
            uiState.isLoading || uiState.isPaginationLoading,
            showClearButton,
            onClearInputClick,
            onSearch
        )

        when {
            uiState.errorMessageId != null -> {
                ErrorComponent(
                    message = stringResource(uiState.errorMessageId),
                    modifier = Modifier.weight(1f)
                )
            }

            uiState.characters.isNotEmpty() -> {
                CharacterList(
                    modifier = Modifier.weight(1f),
                    characters = uiState.characters,
                    query = uiState.query,
                    paginationErrorId = uiState.paginationErrorId,
                    isPaginationLoading = uiState.isPaginationLoading,
                    onCharacterClick = onCharacterClick,
                    onLoadNextPage = onLoadNextPage,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope
                )
            }

            !uiState.isLoading && uiState.hasSearched -> {
                EmptyResultsComponent(modifier = Modifier.weight(1f))
            }
        }
    }
}
