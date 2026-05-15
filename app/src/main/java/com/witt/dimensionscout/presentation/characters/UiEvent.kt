package com.witt.dimensionscout.presentation.characters

import com.witt.dimensionscout.domain.model.Character

sealed interface UiEvent {
    data class ShareCharacter(val character: Character) : UiEvent
    data object Idle : UiEvent
}
