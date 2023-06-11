package ru.verb.astonfinalproject.core

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.RemoteMediator

@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator<K : Any,V: Any>: RemoteMediator<K, V>() {
    protected var pageIndex = 1

    companion object {
        const val PAGE_SIZE = 20
    }

    protected fun getPageIndex(loadType: LoadType): Int? {
        pageIndex = when(loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return null
            LoadType.APPEND -> ++pageIndex
        }
        return pageIndex
    }
}