package com.witt.dimensionscout.data.repository

import android.util.Log
import com.witt.dimensionscout.data.remote.CharacterApiService
import com.witt.dimensionscout.domain.model.RMResponse
import com.witt.dimensionscout.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

class CharacterRepositoryImpl(private val apiService: CharacterApiService) : CharacterRepository {
    override suspend fun getCharacters(query: String): RMResponse = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getCharacters(query)
            RMResponse.Success(data = response)
        } catch (e: IOException) {
            Log.e(TAG, "IOException calling getCharacters: ${e.message}")
            RMResponse.Error("Please check your internet connection and try again.")
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException calling getCharacters with code ${e.code()}: ${e.message}")
            when (e.code()) {
                400, 404 -> RMResponse.Error("No results found, try another search.")
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