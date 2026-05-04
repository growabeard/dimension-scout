package com.witt.dimensionscout.domain.model

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val image: String,
    val origin: String,
    val created: String,
    val displayDate: String
)