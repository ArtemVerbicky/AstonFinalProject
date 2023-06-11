package ru.verb.astonfinalproject.data.entity

import androidx.room.*
import ru.verb.astonfinalproject.data.database.converter.CharacterGenderConverter
import ru.verb.astonfinalproject.data.database.converter.CharacterStatusConverter
import ru.verb.astonfinalproject.data.database.converter.ListConverter
import ru.verb.astonfinalproject.domain.models.Character
import ru.verb.astonfinalproject.domain.models.CharacterLocation
import ru.verb.astonfinalproject.domain.models.Origin
import ru.verb.astonfinalproject.domain.enumeration.CharacterGender
import ru.verb.astonfinalproject.domain.enumeration.CharacterStatus

@Entity
@TypeConverters(ListConverter::class, CharacterGenderConverter::class, CharacterStatusConverter::class)
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String?,
    val gender: CharacterGender,
    @Embedded
    val origin: OriginEntity,
    @Embedded
    val characterLocation: CharacterLocationEntity?,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
) {
    companion object {
        fun toEntity(character: Character) = CharacterEntity(
            id = character.id,
            name = character.name,
            status = character.status,
            species = character.species,
            type = character.type,
            gender = character.gender,
            origin = OriginEntity.toEntity(character.origin),
            characterLocation = character.characterLocation?.let {
                CharacterLocationEntity.toEntity(it)
            },
            image = character.image,
            episode = character.episode,
            url = character.url,
            created = character.created
        )
    }

    fun fromEntity(): Character = Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.fromEntity(),
        characterLocation = characterLocation?.fromEntity(),
        image = image,
        episode = episode,
        url = url,
        created = created
    )
}


data class OriginEntity(
    @ColumnInfo(name = "origin_name")
    val name: String,
    @ColumnInfo(name = "origin_url")
    val url: String
) {
    companion object {
        fun toEntity(origin: Origin) = OriginEntity(
            name = origin.name,
            url = origin.url
        )
    }

    fun fromEntity() = Origin(
        name = name,
        url = url
    )
}

data class CharacterLocationEntity(
    @ColumnInfo(name = "location_name")
    val name: String,
    @ColumnInfo(name = "location_url")
    val url: String
) {
    companion object {
        fun toEntity(characterLocation: CharacterLocation) = CharacterLocationEntity(
            name = characterLocation.name,
            url = characterLocation.url
        )
    }
    fun fromEntity() = CharacterLocation(
        name = name,
        url = url
    )
}

fun List<CharacterEntity>.fromEntity(): List<Character> = this.map { it.fromEntity() }
fun List<Character>.toEntity(): List<CharacterEntity> = this.map { CharacterEntity.toEntity(it) }