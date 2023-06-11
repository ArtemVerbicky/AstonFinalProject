package ru.verb.astonfinalproject.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.verb.astonfinalproject.data.database.converter.ListConverter
import ru.verb.astonfinalproject.domain.models.Episode

@Entity
@TypeConverters(ListConverter::class)
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val airDate: String?,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
) {
    companion object {
        fun toEntity(episode: Episode) = EpisodeEntity(
            id = episode.id,
            name = episode.name,
            airDate = episode.airDate,
            episode = episode.episode,
            characters = episode.characters,
            url = episode.url,
            created = episode.created
        )
    }

    fun fromEntity() = Episode(
        id = id,
        name = name,
        airDate = airDate,
        episode = episode,
        characters = characters,
        url = url,
        created = created
    )
}

fun List<EpisodeEntity>.fromEntity(): List<Episode> = this.map { it.fromEntity() }
fun List<Episode>.toEntity(): List<EpisodeEntity> = this.map { EpisodeEntity.toEntity(it) }
