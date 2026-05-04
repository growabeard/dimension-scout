package com.witt.dimensionscout.data.repository

import android.util.Log
import com.witt.dimensionscout.R
import com.witt.dimensionscout.data.remote.CharacterApiService
import com.witt.dimensionscout.data.remote.dto.CharacterResponseDto
import com.witt.dimensionscout.data.remote.dto.InfoDto
import com.witt.dimensionscout.data.remote.dto.RMResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class CharacterRepositoryImplTest {

    @RelaxedMockK
    private lateinit var apiService: CharacterApiService

    private lateinit var repository: CharacterRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0

        repository = CharacterRepositoryImpl(apiService)
    }

    @Test
    fun `ensure getCharacters() returns a list of characters when apiService returns a list of characters`() {
        coEvery { apiService.getCharacters(any(), any()) } returns CharacterResponseDto(
            InfoDto(
                0,
                1
            ), listOf()
        )

        val result = runBlocking { repository.getCharacters("", 1) }

        assertTrue(result is RMResponse.Success)
        assertEquals(0, (result as RMResponse.Success).data.size)
    }

    @Test
    fun `ensure getCharacters() returns an empty list of characters when apiService returns a null list of characters`() {
        coEvery { apiService.getCharacters(any(), any()) } returns CharacterResponseDto(
            InfoDto(
                0,
                1
            ), null
        )

        val result = runBlocking { repository.getCharacters("", 1) }

        assertTrue(result is RMResponse.Success)
        assertEquals(0, (result as RMResponse.Success).data.size)
    }

    @Test
    fun `ensure getCharacters() returns an error when apiService throws an exception`() {
        coEvery { apiService.getCharacters(any(), any()) } throws Exception()

        val result = runBlocking { repository.getCharacters("", 1) }

        assertTrue(result is RMResponse.Error)
        assertEquals(R.string.error_generic_exception, (result as RMResponse.Error).messageId)
    }

    @Test
    fun `ensure getCharacters() returns an error when apiService returns a 400 error`() = runTest {
        val response = retrofit2.Response.error<Any>(400, "".toResponseBody(null))
        coEvery { apiService.getCharacters(any(), any()) } throws HttpException(response)

        val result = repository.getCharacters("", 1)

        assertTrue(result is RMResponse.Error)
        assertEquals(R.string.error_http_400, (result as RMResponse.Error).messageId)
    }

    @Test
    fun `ensure getCharacters() returns success with empty list when apiService returns a 404 error`() =
        runTest {
            val response = retrofit2.Response.error<Any>(404, "".toResponseBody(null))
            coEvery { apiService.getCharacters(any(), any()) } throws HttpException(response)

            val result = repository.getCharacters("", 1)

            assertTrue(result is RMResponse.Success)
            assertTrue((result as RMResponse.Success).data.isEmpty())
            assertFalse(result.hasNextPage)
        }

    @Test
    fun `ensure getCharacters() returns an error when apiService returns a 429 error`() = runTest {
        val response = retrofit2.Response.error<Any>(429, "".toResponseBody(null))
        coEvery { apiService.getCharacters(any(), any()) } throws HttpException(response)

        val result = repository.getCharacters("", 1)

        assertTrue(result is RMResponse.Error)
        assertEquals(R.string.error_http_429, (result as RMResponse.Error).messageId)
    }

    @Test
    fun `ensure getCharacters() returns an error when apiService throws an IOException`() =
        runTest {
            coEvery {
                apiService.getCharacters(
                    any(),
                    any()
                )
            } throws java.io.IOException("Network Failure")

            val result = repository.getCharacters("", 1)

            assertTrue(result is RMResponse.Error)
            assertEquals(R.string.error_io_exception, (result as RMResponse.Error).messageId)
        }
}