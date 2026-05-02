package com.witt.dimensionscout.presentation.characters

import com.witt.dimensionscout.domain.model.Character

data class CharacterSearchState(
    val characters: List<Character> = emptyList(),
    val currentPage: Int = 0,
    val canLoadMore: Boolean = true,
    val query: String = "",
    val isLoading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val hasSearched: Boolean = false,
    val errorMessageId: Int? = null,
    val paginationErrorId: Int? = null
)