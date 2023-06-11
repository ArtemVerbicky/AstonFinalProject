package ru.verb.astonfinalproject.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.verb.astonfinalproject.core.BaseRepository
import ru.verb.astonfinalproject.domain.models.Location

interface LocationRepository: BaseRepository<Location> {
    override fun getPagedItems(): Flow<PagingData<Location>>
}