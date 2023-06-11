package ru.verb.astonfinalproject.data.repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import okio.IOException
import ru.verb.astonfinalproject.core.BaseRemoteMediator
import ru.verb.astonfinalproject.data.database.dao.CharacterDao
import ru.verb.astonfinalproject.data.entity.CharacterEntity
import ru.verb.astonfinalproject.data.network.api.CharacterApi
import ru.verb.astonfinalproject.domain.error.ApiError
import ru.verb.astonfinalproject.domain.error.NetworkError
import javax.inject.Inject

class CharacterRemoteMediator @Inject constructor(
    private val characterDao: CharacterDao,
    private val characterApi: CharacterApi
) : BaseRemoteMediator<Int, CharacterEntity>() {

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)
        val limit = state.config.pageSize

        try {
            val response = characterApi.getPagedItems(pageIndex)
            if (!response.isSuccessful) return MediatorResult.Error(
                ApiError(
                    response.code().toString()
                )
            )
            val body =
                response.body() ?: return MediatorResult.Error(ApiError(response.code().toString()))

            if (loadType == LoadType.REFRESH) {
                characterDao.refresh(body.results)
            } else {
                characterDao.save(body.results)
            }
            return MediatorResult.Success(endOfPaginationReached = body.results.size < limit)
        } catch (ioException: IOException) {
            return MediatorResult.Error(NetworkError)
        } catch (runtimeException: RuntimeException) {
            return MediatorResult.Error(runtimeException)
        }
    }
}