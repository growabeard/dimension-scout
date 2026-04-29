package com.witt.dimensionscout.domain.repository

import com.witt.dimensionscout.domain.model.RMResponse

interface CharacterRepository {
    suspend fun getCharacters(query: String): RMResponse
}