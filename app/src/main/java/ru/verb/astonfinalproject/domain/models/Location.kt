package ru.verb.astonfinalproject.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: String,
    val url: String,
    val created: String
): Parcelable
