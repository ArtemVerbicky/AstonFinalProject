package ru.verb.astonfinalproject.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.verb.astonfinalproject.network.api.CharacterApi
import ru.verb.astonfinalproject.network.api.EpisodeApi
import ru.verb.astonfinalproject.network.api.LocationApi

const val BASE_URL = "https://rickandmortyapi.com/api/"

object ConnectionService {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofitClient = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    object CharacterApiService {
        val service: CharacterApi by lazy {
            retrofitClient.create(CharacterApi::class.java)
        }
    }

    object EpisodeApiService {
        val service: EpisodeApi by lazy {
            retrofitClient.create(EpisodeApi::class.java)
        }
    }

    object LocationApiService {
        val service: LocationApi by lazy {
            retrofitClient.create(LocationApi::class.java)
        }
    }
}