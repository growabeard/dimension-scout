package com.witt.dimensionscout.domain.use_case

import com.witt.dimensionscout.data.model.RMResponse
import com.witt.dimensionscout.domain.repository.CharacterRepository

class GetCharacterUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(query: String): RMResponse {
        return repository.getCharacters(query)
    }
}