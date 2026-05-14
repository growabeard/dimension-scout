package com.witt.dimensionscout.data.repository

import android.util.Log
import com.witt.dimensionscout.R
import com.witt.dimensionscout.data.remote.CharacterApiService
import com.witt.dimensionscout.data.remote.dto.RMResponse
import com.witt.dimensionscout.domain.repository.CharacterRepository
import okio.IOException
import retrofit2.HttpException
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_NOT_FOUND

class CharacterRepositoryImpl(private val apiService: CharacterApiService) : CharacterRepository {

    private var cachedCharacters: MutableMap<Pair<String, Int>, RMResponse> = mutableMapOf()
    override suspend fun getCharacters(query: String, page: Int): RMResponse {
        try {
            val cachedResponse = cachedCharacters[Pair(query, page)]
            if (cachedResponse != null) {
                return cachedResponse
            } else {
                val dto = apiService.getCharacters(query, page)
                val results = dto.results ?: emptyList()
                val response = RMResponse.Success(
                    data = results.map { it.toDomain() },
                    hasNextPage = dto.info != null && dto.info.next != null
                )
                cachedCharacters[Pair(query, page)] = response
                return response
            }
        } catch (e: IOException) {
            Log.e(TAG, "IOException calling getCharacters: ${e.message}")
            return RMResponse.Error(R.string.error_io_exception)
        } catch (e: HttpException) {
            Log.e(
                TAG,
                "HttpException calling getCharacters with code ${e.code()}: ${e.message}"
            )
            return when (e.code()) {
                HTTP_NOT_FOUND -> RMResponse.Success(emptyList(), hasNextPage = false)
                HTTP_BAD_REQUEST -> RMResponse.Error(R.string.error_http_400)
                429 -> RMResponse.Error(R.string.error_http_429)
                else -> RMResponse.Error(R.string.error_http_exception)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception calling getCharacters: ${e.message}")
            return RMResponse.Error(R.string.error_generic_exception)
        }
    }

    companion object {
        private const val TAG = "CharacterRepositoryImpl"
    }
}