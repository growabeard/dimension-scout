package com.witt.dimensionscout.domain.model

data class CharacterResponse(
    val info: Info,
    val results: List<RMCharacter>
) {
    companion object {
        val EMPTY = CharacterResponse(
            info = Info(count = 0, pages = 0),
            results = emptyList()
        )
    }
}

data class Info(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null
)

data class RMCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

data class Origin(
    val name: String,
    val url: String
)

data class Location(
    val name: String,
    val url: String
)
