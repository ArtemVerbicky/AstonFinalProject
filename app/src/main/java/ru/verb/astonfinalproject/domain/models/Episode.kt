package ru.verb.astonfinalproject.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(
    val id: Int,
    val name: String,
    val airDate: String?,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
) : Parcelable
