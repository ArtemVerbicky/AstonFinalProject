package ru.verb.astonfinalproject.data.network.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.verb.astonfinalproject.data.dto.LocationApiResponse
import ru.verb.astonfinalproject.data.entity.LocationEntity

interface LocationApi {
    @GET("location")
    suspend fun getPagedItems(@Query("page") page: Int): Response<LocationApiResponse>

    @GET("location/{id}")
    suspend fun getItemById(@Path("id") id: Int): Response<LocationEntity>

    @GET("location/{ids}")
    suspend fun getMultipleItems(@Path("ids") ids: List<Int>): Response<List<LocationEntity>>
}