package ru.verb.astonfinalproject.data.dto

import ru.verb.astonfinalproject.data.entity.LocationEntity

data class LocationApiResponse(
    val info: Info,
    val results: List<LocationEntity>
) {
}