package ru.verb.astonfinalproject.core

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface BaseRepository<T : Any> {
    val item: StateFlow<T?>
    val items: StateFlow<List<T>?>
    fun getPagedItems(): Flow<PagingData<T>>
    suspend fun getMultipleItems(ids: List<Int>)
    suspend fun getItem(id: Int)
}