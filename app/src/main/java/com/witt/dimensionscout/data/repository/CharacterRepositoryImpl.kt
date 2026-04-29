package com.witt.dimensionscout.data.repository

import com.witt.dimensionscout.data.remote.CharacterApiService
import com.witt.dimensionscout.domain.model.RMResponse
import com.witt.dimensionscout.domain.repository.CharacterRepository

class CharacterRepositoryImpl(private val apiService: CharacterApiService) : CharacterRepository {
    override suspend fun getCharacters(query: String): RMResponse {
        return try {
            val response = apiService.getCharacters(query)
            RMResponse.Success(data = response)
        } catch (e: Exception) {
            RMResponse.Error(message = e.localizedMessage ?: "An unexpected error occurred")
        }
    }
}