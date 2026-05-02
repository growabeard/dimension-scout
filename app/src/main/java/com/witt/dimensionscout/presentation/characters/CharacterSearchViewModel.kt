package com.witt.dimensionscout.presentation.characters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.witt.dimensionscout.data.model.RMResponse
import com.witt.dimensionscout.domain.use_case.GetCharacterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterSearchViewModel(private val useCase: GetCharacterUseCase) : ViewModel() {

    private val _state = MutableStateFlow(CharacterSearchState())
    val state = _state.asStateFlow()

    val showClearButton: Boolean
        get() = _state.value.query.isNotEmpty()

    fun onQueryChange(newQuery: String) {
        Log.d(TAG, "onQueryChange: $newQuery")
        _state.update { it.copy(query = newQuery) }
        getCharacters()
    }

    fun clearInput() {
        Log.d(TAG, "clearInput")
        _state.update { it.copy(query = "") }
    }

    fun onCharacterClick(index: Int): Int {
        Log.d(TAG, "onCharacterClick: $index")
        val character = _state.value.characters[index]
        Log.d(TAG, "onCharacterClick: $character")
        return character.id
    }

    fun getCharacters() {
        Log.d(TAG, "getCharacters")
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, hasSearched = true) }
            when (val response = useCase.invoke(_state.value.query)) {
                is RMResponse.Success -> {
                    _state.update { it.copy(characters = response.data, error = null) }
                }

                is RMResponse.Error -> {
                    _state.update { it.copy(error = response.message) }
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    companion object {
        private const val TAG = "CharacterSearchViewModel"
    }
}