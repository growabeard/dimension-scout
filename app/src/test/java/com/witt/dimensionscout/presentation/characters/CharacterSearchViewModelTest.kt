package com.witt.dimensionscout.presentation.characters

import android.util.Log
import com.witt.dimensionscout.R
import com.witt.dimensionscout.data.remote.dto.RMResponse
import com.witt.dimensionscout.domain.model.Character
import com.witt.dimensionscout.domain.use_case.GetCharacterUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterSearchViewModelTest {

    private lateinit var viewModel: CharacterSearchViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val characterList = listOf(
        Character(
            name = "Rick Sanchez",
            id = 1,
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = "Earth",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:48:46.250Z",
            displayDate = "November 4, 2017"
        ),
        Character(
            name = "Morty Smith",
            id = 2,
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = "Earth",
            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
            url = "https://rickandmortyapi.com/api/character/2",
            created = "2017-11-04T18:50:21.651Z",
            displayDate = "November 4, 2017"
        )
    )

    @RelaxedMockK
    private lateinit var useCase: GetCharacterUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0

        viewModel = CharacterSearchViewModel(useCase)
    }

    @Test
    fun `ensure getCharacters() calls useCase`() = runTest(testDispatcher) {
        coEvery { useCase.invoke(any()) }.returns(
            RMResponse.Success(
                data = characterList
            )
        )

        viewModel.onQueryChange("Rick")

        advanceUntilIdle()

        coVerify { useCase.invoke(any()) }
        assertEquals(2, viewModel.state.value.characters.size)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `ensure getCharacters() sets error in state if useCase returns error`() =
        runTest(testDispatcher) {
            coEvery { useCase.invoke(any()) }.returns(RMResponse.Error(R.string.error_generic_exception))

            viewModel.onQueryChange("Morty")

            advanceUntilIdle()

            coVerify { useCase.invoke(any()) }
            assertEquals(R.string.error_generic_exception, viewModel.state.value.errorMessageId)
            assertEquals(false, viewModel.state.value.isLoading)
        }

    @Test
    fun `ensure clearInput() sets query to empty`() = runTest(testDispatcher) {
        viewModel.onQueryChange("Morty")
        assertEquals("Morty", viewModel.state.value.query)

        viewModel.clearInput()
        assertEquals("", viewModel.state.value.query)
    }

    @Test
    fun `ensure showClearButton is false when query is empty`() {
        viewModel.onQueryChange("")

        assertEquals(false, viewModel.showClearButton)
    }

    @Test
    fun `ensure showClearButton is true when query is populated`() {
        viewModel.onQueryChange("Rick")

        assertEquals(true, viewModel.showClearButton)
    }

    @Test
    fun `ensure onCharacterClick() returns character id`() = runTest {
        coEvery { useCase.invoke(any()) }.returns(
            RMResponse.Success(
                data = characterList
            )
        )

        viewModel.onQueryChange("Test")

        advanceUntilIdle()

        val characterId = viewModel.onCharacterClick(0)

        assertEquals(1, characterId)
    }

    @Test
    fun `ensure onSearch() calls getCharacters()`() = runTest {
        coEvery { useCase.invoke(any()) }.returns(
            RMResponse.Success(
                data = characterList
            )
        )

        viewModel.onSearch()

        advanceUntilIdle()

        coVerify { useCase.invoke(any()) }
    }

}