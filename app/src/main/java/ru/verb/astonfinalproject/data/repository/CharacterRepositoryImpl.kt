package ru.verb.astonfinalproject.data.repository

import androidx.paging.*
import kotlinx.coroutines.flow.*
import okio.IOException
import ru.verb.astonfinalproject.core.BaseRemoteMediator.Companion.PAGE_SIZE
import ru.verb.astonfinalproject.data.database.dao.CharacterDao
import ru.verb.astonfinalproject.data.entity.fromEntity
import ru.verb.astonfinalproject.data.network.ConnectionService
import ru.verb.astonfinalproject.data.repository.mediator.CharacterRemoteMediator
import ru.verb.astonfinalproject.domain.CharacterRepository
import ru.verb.astonfinalproject.domain.models.Character
import ru.verb.astonfinalproject.domain.error.ApiError
import ru.verb.astonfinalproject.domain.error.NetworkError
import ru.verb.astonfinalproject.domain.error.UnknownError
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao,
    private val characterRemoteMediator: CharacterRemoteMediator
) : CharacterRepository {
    private val _item = MutableStateFlow<Character?>(null)
    override val item: StateFlow<Character?> = _item

    private val _items = MutableStateFlow<List<Character>?>(null)
    override val items: StateFlow<List<Character>?> = _items


    override suspend fun getItem(id: Int) {
        try {
            val response = ConnectionService.CharacterApiService.service.getItemById(id)
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
            val response = ConnectionService.CharacterApiService.service.getMultipleItems(ids)
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

    override fun getPagedItems(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE / 2,
                enablePlaceholders = false
            ),
            remoteMediator = characterRemoteMediator,
            pagingSourceFactory = { characterDao.getAll() }
        ).flow.map {
            it.map { characterEntity ->
                characterEntity.fromEntity()
            }
        }
    }

}