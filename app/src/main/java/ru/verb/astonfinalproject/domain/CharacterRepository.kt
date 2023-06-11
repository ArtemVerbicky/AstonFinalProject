package ru.verb.astonfinalproject.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.verb.astonfinalproject.core.BaseRepository
import ru.verb.astonfinalproject.domain.models.Character

interface CharacterRepository: BaseRepository<Character> {
    override fun getPagedItems(): Flow<PagingData<Character>>
}