package ru.verb.astonfinalproject.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import okio.IOException
import ru.verb.astonfinalproject.domain.models.Character
import ru.verb.astonfinalproject.error.ApiError
import ru.verb.astonfinalproject.error.NetworkError
import ru.verb.astonfinalproject.error.UnknownError
import ru.verb.astonfinalproject.network.ConnectionService

class CharacterPagingSource: PagingSource<Int, Character>() {

    companion object {
        const val PAGE_SIZE = 20
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val pageIndex = params.key ?: 1
        try {
            val response = ConnectionService.CharacterApiService.service.getPagedItems(pageIndex)
            if (!response.isSuccessful) {
                throw ApiError(response.code().toString())
            }
            val body = response.body() ?: throw ApiError(response.code().toString())
            return LoadResult.Page(
                data = body,
                prevKey = if (pageIndex == 1) null else pageIndex - 1,
                nextKey = if (body.size == params.loadSize) pageIndex + (params.loadSize / PAGE_SIZE) else null
            )
        } catch (exception: IOException) {
            return LoadResult.Error(
                throwable = NetworkError
            )
        } catch (exception: RuntimeException) {
            return LoadResult.Error(
                throwable = UnknownError
            )
        }
    }
}