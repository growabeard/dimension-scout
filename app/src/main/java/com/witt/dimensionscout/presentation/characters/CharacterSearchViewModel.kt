package com.witt.dimensionscout.presentation.characters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.witt.dimensionscout.data.remote.dto.RMResponse
import com.witt.dimensionscout.domain.use_case.GetCharacterUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterSearchViewModel(private val useCase: GetCharacterUseCase) : ViewModel() {

    private val _state = MutableStateFlow(CharacterSearchState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    val showClearButton: Boolean
        get() = _state.value.query.isNotEmpty()

    init {
        searchJob = viewModelScope.launch {
            getCharacters()
        }
    }

    fun onQueryChange(newQuery: String) {
        Log.d(TAG, "onQueryChange: $newQuery")
        _state.update { it.copy(query = newQuery) }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300L)
            getCharacters()
        }
    }

    fun clearInput() {
        Log.d(TAG, "clearInput")
        _state.update { it.copy(query = "") }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            getCharacters()
        }
    }

    fun onCharacterClick(index: Int): Int {
        Log.d(TAG, "onCharacterClick: $index")
        val character = _state.value.characters[index]
        Log.d(TAG, "onCharacterClick: $character")
        return character.id
    }

    private suspend fun getCharacters() {
        Log.d(TAG, "getCharacters with query: ${_state.value.query}")
        _state.update { it.copy(isLoading = true, hasSearched = true) }

        when (val response = useCase.invoke(_state.value.query)) {
            is RMResponse.Success -> {
                _state.update { it.copy(characters = response.data, errorMessageId = null) }
            }

            is RMResponse.Error -> {
                _state.update { it.copy(errorMessageId = response.messageId) }
            }
        }

        _state.update { it.copy(isLoading = false) }
    }
    
    fun onSearch() {
        Log.d(TAG, "onSearch triggered manually")
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            getCharacters()
        }
    }


    companion object {
        private const val TAG = "CharacterSearchViewModel"
    }
}