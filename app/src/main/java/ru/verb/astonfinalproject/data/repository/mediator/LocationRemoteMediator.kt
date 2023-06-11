package ru.verb.astonfinalproject.data.repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import okio.IOException
import ru.verb.astonfinalproject.core.BaseRemoteMediator
import ru.verb.astonfinalproject.data.database.dao.LocationDao
import ru.verb.astonfinalproject.data.entity.LocationEntity
import ru.verb.astonfinalproject.data.network.ConnectionService
import ru.verb.astonfinalproject.data.network.api.LocationApi
import ru.verb.astonfinalproject.domain.error.ApiError
import ru.verb.astonfinalproject.domain.error.NetworkError
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class LocationRemoteMediator @Inject constructor(
    private val locationDao: LocationDao,
    private val locationApi: LocationApi
) : BaseRemoteMediator<Int, LocationEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocationEntity>
    ): MediatorResult {
        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)
        val limit = state.config.pageSize

        try {
            val response = locationApi.getPagedItems(pageIndex)
            if (!response.isSuccessful) return MediatorResult.Error(
                ApiError(
                    response.code().toString()
                )
            )
            val body =
                response.body() ?: return MediatorResult.Error(ApiError(response.code().toString()))

            if (loadType == LoadType.REFRESH) {
                locationDao.refresh(body.results)
            } else {
                locationDao.save(body.results)
            }
            return MediatorResult.Success(endOfPaginationReached = body.results.size < limit)
        } catch (ioException: IOException) {
            return MediatorResult.Error(NetworkError)
        } catch (runtimeException: RuntimeException) {
            return MediatorResult.Error(runtimeException)
        }
    }
}