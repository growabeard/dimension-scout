package com.witt.dimensionscout.presentation.characters

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

    init {
        getCharacters()
    }

    fun getCharacters() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val response = useCase.invoke(_state.value.query)
            when (response) {
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
}