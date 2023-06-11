package ru.verb.astonfinalproject.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.verb.astonfinalproject.domain.enumeration.CharacterGender
import ru.verb.astonfinalproject.domain.enumeration.CharacterStatus

@Parcelize
data class Character(

    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String?,
    val gender: CharacterGender,
    val origin: Origin,
    val characterLocation: CharacterLocation?,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
) : Parcelable

@Parcelize
data class Origin(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class CharacterLocation(
    val name: String,
    val url: String
) : Parcelable


