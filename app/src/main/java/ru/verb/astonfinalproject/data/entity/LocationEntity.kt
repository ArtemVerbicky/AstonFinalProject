package ru.verb.astonfinalproject.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.verb.astonfinalproject.data.database.converter.ListConverter
import ru.verb.astonfinalproject.domain.models.Location

@Entity
@TypeConverters(ListConverter::class)
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
) {
    companion object {
        fun toEntity(location: Location) = LocationEntity(
            id = location.id,
            name = location.name,
            type = location.type,
            dimension = location.dimension,
            residents = location.residents,
            url = location.url,
            created = location.created
        )
    }

    fun fromEntity() = Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        residents = residents,
        url = url,
        created = created
    )
}

fun List<LocationEntity>.fromEntity(): List<Location> = this.map { it.fromEntity() }
fun List<Location>.toEntity(): List<LocationEntity> = this.map { LocationEntity.toEntity(it) }
