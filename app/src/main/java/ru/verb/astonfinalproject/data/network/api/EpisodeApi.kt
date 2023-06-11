package ru.verb.astonfinalproject.data.network.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.verb.astonfinalproject.data.dto.EpisodeApiResponse
import ru.verb.astonfinalproject.data.entity.EpisodeEntity

interface EpisodeApi {
    @GET("episode")
    suspend fun getPagedItems(@Query("page") page: Int): Response<EpisodeApiResponse>

    @GET("episode/{id}")
    suspend fun getItemById(@Path("id") id: Int): Response<EpisodeEntity>

    @GET("episode/{ids}")
    suspend fun getMultipleItems(@Path("ids") ids: List<Int>): Response<List<EpisodeEntity>>
}