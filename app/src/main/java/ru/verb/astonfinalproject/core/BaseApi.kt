package ru.verb.astonfinalproject.core

import retrofit2.Response

interface BaseApi<T> {
    fun getPagedItems(page: Int): Response<List<T>>
    fun getItemById(id: Int): Response<T>
}