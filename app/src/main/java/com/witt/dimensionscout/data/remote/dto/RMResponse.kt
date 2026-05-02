package com.witt.dimensionscout.data.remote.dto

import com.witt.dimensionscout.domain.model.Character

sealed interface RMResponse {

    data class Success(val data: List<Character>, val hasNextPage: Boolean) : RMResponse
    data class Error(val messageId: Int) : RMResponse

}