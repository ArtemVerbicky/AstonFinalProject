package ru.verb.astonfinalproject.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.verb.astonfinalproject.data.CharacterPagingSource.Companion.PAGE_SIZE
import ru.verb.astonfinalproject.data.LocationPagingSource
import ru.verb.astonfinalproject.domain.LocationRepository
import ru.verb.astonfinalproject.domain.models.Location

class LocationRepositoryImpl: LocationRepository {
    override suspend fun getPagedLocations(): Flow<PagingData<Location>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE / 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {LocationPagingSource()}
        ).flow
    }
}