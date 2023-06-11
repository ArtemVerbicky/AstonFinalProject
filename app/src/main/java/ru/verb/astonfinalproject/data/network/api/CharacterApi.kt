package ru.verb.astonfinalproject.data.network.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.verb.astonfinalproject.data.dto.CharacterApiResponse
import ru.verb.astonfinalproject.data.entity.CharacterEntity

interface CharacterApi {
    @GET("character")
    suspend fun getPagedItems(@Query("page") page: Int): Response<CharacterApiResponse>

    @GET("character/{id}")
    suspend fun getItemById(@Path("id") id: Int): Response<CharacterEntity>

    @GET("character/{ids}")
    suspend fun getMultipleItems(@Path("ids") ids: List<Int>): Response<List<CharacterEntity>>
}