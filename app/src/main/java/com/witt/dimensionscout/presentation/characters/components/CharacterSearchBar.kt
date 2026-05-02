package com.witt.dimensionscout.presentation.characters.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.witt.dimensionscout.R
import com.witt.dimensionscout.ui.theme.DimensionScoutTheme

@Preview
@Composable
fun CharacterCharacterSearchBarEmptyQueryPreview() {
    DimensionScoutTheme {
        CharacterSearchBar(
            query = "",
            onQueryChange = {},
            loading = false,
            showClearButton = false,
            onClearInputClick = {},
            onSearch = { })
    }
}

@Preview
@Composable
fun CharacterCharacterSearchBarPopulatedQueryPreview() {
    DimensionScoutTheme {
        CharacterSearchBar(
            query = "Rick",
            onQueryChange = {},
            loading = false,
            showClearButton = true,
            onClearInputClick = {},
            onSearch = { })
    }
}

@Preview
@Composable
fun CharacterCharacterSearchBarLoadingQueryPreview() {
    DimensionScoutTheme {
        CharacterSearchBar(
            query = "Rick",
            onQueryChange = {},
            loading = true,
            showClearButton = true,
            onClearInputClick = {},
            onSearch = { })
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    loading: Boolean,
    showClearButton: Boolean,
    onClearInputClick: () -> Unit,
    onSearch: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(SearchBarDefaults.inputFieldShape)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            onValueChange = { onQueryChange(it) },
            placeholder = { Text(stringResource(R.string.search_placeholder)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (showClearButton) {
                    IconButton(onClick = onClearInputClick) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = stringResource(R.string.clear_search_input)
                        )
                    }
                }
            },
            colors = SearchBarDefaults.inputFieldColors().copy(
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent,
                disabledIndicatorColor = Transparent
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch() })
        )
        if (loading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(3.dp)
            )
        }
    }
}