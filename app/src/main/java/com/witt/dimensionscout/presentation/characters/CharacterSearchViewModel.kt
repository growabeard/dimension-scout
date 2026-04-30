package com.witt.dimensionscout.presentation.characters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.witt.dimensionscout.domain.model.RMResponse
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

    fun getCharacters() {
        Log.d(TAG, "getCharacters")
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            when (val response = useCase.invoke(_state.value.query)) {
                is RMResponse.Success -> {
                    _state.update { it.copy(characters = response.data.results) }
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