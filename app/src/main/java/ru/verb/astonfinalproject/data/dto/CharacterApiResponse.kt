package ru.verb.astonfinalproject.data.dto

import ru.verb.astonfinalproject.data.entity.CharacterEntity

data class CharacterApiResponse(
    val info: Info,
    val results: List<CharacterEntity>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

