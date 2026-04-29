package com.witt.dimensionscout.domain.model

sealed interface RMResponse {

    data class Success(val data: CharacterResponse) : RMResponse
    data class Error(val message: String) : RMResponse

}