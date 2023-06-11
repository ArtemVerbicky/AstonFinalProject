package ru.verb.astonfinalproject.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.verb.astonfinalproject.domain.models.Location

interface LocationRepository {
    suspend fun getPagedLocations(): Flow<PagingData<Location>>
}