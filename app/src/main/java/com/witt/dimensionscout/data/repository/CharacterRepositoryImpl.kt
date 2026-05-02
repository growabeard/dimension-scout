package com.witt.dimensionscout.data.repository

import android.util.Log
import com.witt.dimensionscout.data.remote.CharacterApiService
import com.witt.dimensionscout.data.remote.dto.RMResponse
import com.witt.dimensionscout.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_NOT_FOUND

class CharacterRepositoryImpl(private val apiService: CharacterApiService) : CharacterRepository {
    override suspend fun getCharacters(query: String): RMResponse = withContext(Dispatchers.IO) {
        try {
            val dto = apiService.getCharacters(query)
            RMResponse.Success(data = dto.results.map { it.toDomain() })
        } catch (e: IOException) {
            Log.e(TAG, "IOException calling getCharacters: ${e.message}")
            RMResponse.Error("Please check your internet connection and try again.")
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException calling getCharacters with code ${e.code()}: ${e.message}")
            when (e.code()) {
                HTTP_NOT_FOUND -> RMResponse.Success(emptyList())
                HTTP_BAD_REQUEST -> RMResponse.Error("Invalid request, please try again later with different criteria.")
                else -> RMResponse.Error("An error occurred, please try again later.")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception calling getCharacters: ${e.message}")
            RMResponse.Error("An error occurred, please try again later.")
        }
    }

    companion object {
        private const val TAG = "CharacterRepositoryImpl"
    }
}