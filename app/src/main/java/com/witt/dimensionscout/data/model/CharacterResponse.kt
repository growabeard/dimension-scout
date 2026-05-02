package com.witt.dimensionscout.data.model

import android.util.Log
import kotlinx.serialization.Serializable
import com.witt.dimensionscout.domain.model.Character
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Serializable
data class CharacterResponse(
    val info: Info,
    val results: List<RMCharacter>
)

@Serializable
data class Info(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null
)

@Serializable
data class RMCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
) {
    fun toDomain(): Character {
        return Character(
            id = id,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender,
            image = image,
            episode = episode,
            url = url,
            created = created,
            displayDate = buildDisplayDate(),
            origin = origin.name
        )
    }

    private fun buildDisplayDate(): String {
        return try {
            java.time.OffsetDateTime.parse(created)
                .atZoneSameInstant(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
        } catch (e: Exception) {
            Log.e("RMCharacter", "buildDisplayDate: $e")
            created
        }
    }
}

@Serializable
data class Origin(
    val name: String,
    val url: String
)
