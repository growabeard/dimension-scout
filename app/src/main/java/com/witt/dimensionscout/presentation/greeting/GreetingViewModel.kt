package com.witt.dimensionscout.presentation.greeting

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.witt.dimensionscout.domain.use_case.GetGreetingUseCase
import kotlinx.coroutines.launch

class GreetingViewModel(
    private val getGreetingUseCase: GetGreetingUseCase
) : ViewModel() {

    private val _state = mutableStateOf(GreetingState())
    val state: State<GreetingState> = _state

    init {
        getGreeting()
    }

    private fun getGreeting() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            val greeting = getGreetingUseCase()
            _state.value = state.value.copy(
                message = greeting.message,
                isLoading = false
            )
        }
    }
}
