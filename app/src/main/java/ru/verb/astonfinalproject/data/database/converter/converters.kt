package ru.verb.astonfinalproject.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.verb.astonfinalproject.domain.enumeration.CharacterGender
import ru.verb.astonfinalproject.domain.enumeration.CharacterStatus

class ListConverter {
    @TypeConverter
    fun convertListToString(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun convertStringToList(data: String): List<String> {
        val gson = Gson()
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }
}

class CharacterGenderConverter {
    @TypeConverter
    fun convertFromCharacterGender(value: CharacterGender) = value.name

    @TypeConverter
    fun convertToCharacterGender(value: String) = enumValueOf<CharacterGender>(value)
}

class CharacterStatusConverter {
    @TypeConverter
    fun convertFromCharacterStatus(value: CharacterStatus) = value.name

    @TypeConverter
    fun convertToCharacterStatus(value: String) = enumValueOf<CharacterStatus>(value)
}