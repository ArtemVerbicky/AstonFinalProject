package ru.verb.astonfinalproject.network.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.verb.astonfinalproject.domain.models.Character
import ru.verb.astonfinalproject.core.BaseApi

interface CharacterApi: BaseApi<Character> {
    @GET("character")
    override fun getPagedItems(@Query("page") page: Int): Response<List<Character>>

    @GET("character/{id}")
    override fun getItemById(@Path("id")id: Int): Response<Character>
}