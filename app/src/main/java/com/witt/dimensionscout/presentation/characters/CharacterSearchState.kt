package com.witt.dimensionscout.presentation.characters

import com.witt.dimensionscout.domain.model.Character

data class CharacterSearchState(
    val characters: List<Character> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val hasSearched: Boolean = false,
    val error: String? = null
)