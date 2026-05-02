package com.witt.dimensionscout.domain.use_case

import com.witt.dimensionscout.data.remote.dto.RMResponse
import com.witt.dimensionscout.domain.repository.CharacterRepository

class GetCharacterUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(query: String, page: Int): RMResponse {
        return repository.getCharacters(query, page)
    }
}