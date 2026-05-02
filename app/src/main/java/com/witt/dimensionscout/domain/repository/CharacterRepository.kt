package com.witt.dimensionscout.domain.repository

import com.witt.dimensionscout.data.remote.dto.RMResponse

interface CharacterRepository {
    suspend fun getCharacters(query: String, page: Int): RMResponse
}