package com.witt.dimensionscout.presentation.characters

import com.witt.dimensionscout.domain.model.RMCharacter

data class CharacterSearchState(
    val characters: List<RMCharacter> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)