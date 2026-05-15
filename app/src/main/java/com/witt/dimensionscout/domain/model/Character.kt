package com.witt.dimensionscout.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable