package ru.verb.astonfinalproject.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.verb.astonfinalproject.data.CharacterPagingSource
import ru.verb.astonfinalproject.data.CharacterPagingSource.Companion.PAGE_SIZE
import ru.verb.astonfinalproject.domain.CharacterRepository
import ru.verb.astonfinalproject.domain.models.Character

class CharacterRepositoryImpl() : CharacterRepository {

    override suspend fun getPagedCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE / 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {CharacterPagingSource()}
        ).flow
    }

}