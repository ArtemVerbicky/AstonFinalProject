package ru.verb.astonfinalproject.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.verb.astonfinalproject.domain.models.Character

interface CharacterRepository {
    suspend fun getPagedCharacters(): Flow<PagingData<Character>>
}