package ru.verb.astonfinalproject.data.dto

import ru.verb.astonfinalproject.data.entity.EpisodeEntity

data class EpisodeApiResponse(
    val info: Info,
    val results: List<EpisodeEntity>
)