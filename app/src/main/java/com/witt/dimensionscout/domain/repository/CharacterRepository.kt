package com.witt.dimensionscout.domain.repository

import com.witt.dimensionscout.data.model.RMResponse

interface CharacterRepository {
    suspend fun getCharacters(query: String): RMResponse
}