package com.witt.dimensionscout.data.model

import com.witt.dimensionscout.domain.model.Character

sealed interface RMResponse {

    data class Success(val data: List<Character>) : RMResponse
    data class Error(val message: String) : RMResponse

}