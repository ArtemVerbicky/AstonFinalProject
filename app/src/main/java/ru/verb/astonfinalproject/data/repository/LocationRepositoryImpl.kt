package ru.verb.astonfinalproject.data.repository

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import okio.IOException
import ru.verb.astonfinalproject.core.BaseRemoteMediator.Companion.PAGE_SIZE
import ru.verb.astonfinalproject.data.database.dao.LocationDao
import ru.verb.astonfinalproject.data.entity.fromEntity
import ru.verb.astonfinalproject.data.network.ConnectionService
import ru.verb.astonfinalproject.data.repository.mediator.LocationRemoteMediator
import ru.verb.astonfinalproject.domain.LocationRepository
import ru.verb.astonfinalproject.domain.models.Location
import ru.verb.astonfinalproject.domain.error.ApiError
import ru.verb.astonfinalproject.domain.error.NetworkError
import ru.verb.astonfinalproject.domain.error.UnknownError
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class LocationRepositoryImpl @Inject constructor(
    private val locationDao: LocationDao,
    private val locationRemoteMediator: LocationRemoteMediator
) : LocationRepository {
    private val _item = MutableStateFlow<Location?>(null)
    override val item: StateFlow<Location?> = _item

    private val _items = MutableStateFlow<List<Location>?>(null)
    override val items: StateFlow<List<Location>?> = _items

    override suspend fun getItem(id: Int) {
        try {
            val response = ConnectionService.LocationApiService.service.getItemById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code().toString())
            }
            val body = response.body() ?: throw ApiError(response.code().toString())
            _item.value = body.fromEntity()
        } catch (exception: IOException) {
            throw NetworkError
        } catch (exception: RuntimeException) {
            throw UnknownError
        }
    }

    override suspend fun getMultipleItems(ids: List<Int>) {
        try {
            val response = ConnectionService.LocationApiService.service.getMultipleItems(ids)
            if (!response.isSuccessful) {
                throw ApiError(response.code().toString())
            }
            val body = response.body() ?: throw ApiError(response.code().toString())
            _items.value = body.fromEntity()
        } catch (exception: IOException) {
            throw NetworkError
        } catch (exception: RuntimeException) {
            throw UnknownError
        }
    }


    override fun getPagedItems(): Flow<PagingData<Location>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE / 2,
                enablePlaceholders = false
            ),
            remoteMediator = locationRemoteMediator,
            pagingSourceFactory = { locationDao.getAll() }
        ).flow.map {
            it.map { locationEntity ->
                locationEntity.fromEntity()
            }
        }
    }


}