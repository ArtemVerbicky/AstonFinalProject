package ru.verb.astonfinalproject.network.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.verb.astonfinalproject.core.BaseApi
import ru.verb.astonfinalproject.domain.models.Location

interface LocationApi: BaseApi<Location> {
    @GET("location")
    override fun getPagedItems(@Query("page") page: Int): Response<List<Location>>

    @GET("location/{id}")
    override fun getItemById(@Path("id") id: Int): Response<Location>
}