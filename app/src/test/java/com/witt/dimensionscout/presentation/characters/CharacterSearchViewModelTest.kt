package com.witt.dimensionscout.presentation.characters

import com.witt.dimensionscout.domain.model.CharacterResponse
import com.witt.dimensionscout.domain.model.Info
import com.witt.dimensionscout.domain.model.Location
import com.witt.dimensionscout.domain.model.Origin
import com.witt.dimensionscout.domain.model.RMCharacter
import com.witt.dimensionscout.domain.model.RMResponse
import com.witt.dimensionscout.domain.use_case.GetCharacterUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
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

    @RelaxedMockK
    private lateinit var useCase: GetCharacterUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `ensure getCharacters() calls useCase`() = runTest(testDispatcher) {
        coEvery { useCase.invoke(any()) }.returns(
            RMResponse.Success(
                data = CharacterResponse(
                    info = Info(
                        count = 2,
                        pages = 1,
                        next = null,
                        prev = null
                    ),
                    results = listOf(
                        RMCharacter(
                            name = "Rick Sanchez",
                            id = 1,
                            status = "Alive",
                            species = "Human",
                            type = "",
                            gender = "Male",
                            origin = Origin(name = "Earth", url = ""),
                            location = Location(name = "Earth", url = ""),
                            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
                            url = "https://rickandmortyapi.com/api/character/1",
                            created = "2017-11-04T18:48:46.250Z"
                        ),
                        RMCharacter(
                            name = "Morty Smith",
                            id = 2,
                            status = "Alive",
                            species = "Human",
                            type = "",
                            gender = "Male",
                            origin = Origin(name = "Earth", url = ""),
                            location = Location(name = "Earth", url = ""),
                            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                            episode = listOf("https://rickandmortyapi.com/api/episode/1"),
                            url = "https://rickandmortyapi.com/api/character/2",
                            created = "2017-11-04T18:50:21.651Z"
                        )
                    )
                )
            )
        )

        viewModel = CharacterSearchViewModel(useCase)


        viewModel.getCharacters()

        advanceUntilIdle()

        coVerify { useCase.invoke(any()) }
        assertEquals(2, viewModel.state.value.characters.size)
        assertEquals(false, viewModel.state.value.isLoading)
    }

    @Test
    fun `ensure getCharacters() sets error in state if useCase returns error`() = runTest(testDispatcher) {
        val errorMessage = "Fake error"
        coEvery { useCase.invoke(any()) }.returns(RMResponse.Error(errorMessage))

        viewModel.getCharacters()

        advanceUntilIdle()

        coVerify { useCase.invoke(any()) }
        assertEquals(errorMessage, viewModel.state.value.error)
        assertEquals(false, viewModel.state.value.isLoading)
    }

}