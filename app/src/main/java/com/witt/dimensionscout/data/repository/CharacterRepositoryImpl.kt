package com.witt.dimensionscout.data.repository

import android.util.Log
import com.witt.dimensionscout.R
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
    override suspend fun getCharacters(query: String, page: Int): RMResponse =
        withContext(Dispatchers.IO) {
            try {
                val dto = apiService.getCharacters(query, page)
                val results = dto.results ?: emptyList()
                RMResponse.Success(
                    data = results.map { it.toDomain() },
                    hasNextPage = dto.info != null && dto.info.next != null
                )
            } catch (e: IOException) {
                Log.e(TAG, "IOException calling getCharacters: ${e.message}")
                RMResponse.Error(R.string.error_io_exception)
            } catch (e: HttpException) {
                Log.e(
                    TAG,
                    "HttpException calling getCharacters with code ${e.code()}: ${e.message}"
                )
                when (e.code()) {
                    HTTP_NOT_FOUND -> RMResponse.Success(emptyList(), hasNextPage = false)
                    HTTP_BAD_REQUEST -> RMResponse.Error(R.string.error_http_400)
                    else -> RMResponse.Error(R.string.error_http_exception)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception calling getCharacters: ${e.message}")
                RMResponse.Error(R.string.error_generic_exception)
            }
        }

    companion object {
        private const val TAG = "CharacterRepositoryImpl"
    }
}