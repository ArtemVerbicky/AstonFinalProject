package ru.verb.astonfinalproject.data.repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import okio.IOException
import ru.verb.astonfinalproject.core.BaseRemoteMediator
import ru.verb.astonfinalproject.data.database.dao.EpisodeDao
import ru.verb.astonfinalproject.data.entity.EpisodeEntity
import ru.verb.astonfinalproject.data.network.ConnectionService
import ru.verb.astonfinalproject.data.network.api.EpisodeApi
import ru.verb.astonfinalproject.domain.error.ApiError
import ru.verb.astonfinalproject.domain.error.NetworkError
import javax.inject.Inject

@ExperimentalPagingApi
class EpisodeRemoteMediator @Inject constructor(
    private val episodeDao: EpisodeDao,
    private val episodeApi: EpisodeApi
) : BaseRemoteMediator<Int, EpisodeEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EpisodeEntity>
    ): MediatorResult {
        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)
        val limit = state.config.pageSize

        try {
            val response = episodeApi.getPagedItems(pageIndex)
            if (!response.isSuccessful) return MediatorResult.Error(
                ApiError(
                    response.code().toString()
                )
            )
            val body =
                response.body() ?: return MediatorResult.Error(ApiError(response.code().toString()))

            if (loadType == LoadType.REFRESH) {
                episodeDao.refresh(body.results)
            } else {
                episodeDao.save(body.results)
            }
            return MediatorResult.Success(endOfPaginationReached = body.results.size < limit)
        } catch (ioException: IOException) {
            return MediatorResult.Error(NetworkError)
        } catch (runtimeException: RuntimeException) {
            return MediatorResult.Error(runtimeException)
        }
    }
}