package com.witt.dimensionscout.data.remote

import com.witt.dimensionscout.data.remote.dto.CharacterResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApiService {
    @GET("character")
    suspend fun getCharacters(@Query("name") query: String): CharacterResponseDto
}