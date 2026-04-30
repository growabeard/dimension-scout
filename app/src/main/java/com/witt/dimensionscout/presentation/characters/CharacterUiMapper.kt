package com.witt.dimensionscout.presentation.characters

import com.witt.dimensionscout.domain.model.RMCharacter
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun RMCharacter.getDisplayDate(): String {
    return try {
        java.time.OffsetDateTime.parse(created)
            .atZoneSameInstant(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
    } catch (e: Exception) {
        created
    }
}