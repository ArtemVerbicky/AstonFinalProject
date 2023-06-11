package ru.verb.astonfinalproject.data.network

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.verb.astonfinalproject.data.network.api.CharacterApi
import ru.verb.astonfinalproject.data.network.api.EpisodeApi
import ru.verb.astonfinalproject.data.network.api.LocationApi
import ru.verb.astonfinalproject.data.network.di.BASE_URL
import java.util.concurrent.TimeUnit



object ConnectionService {
    private val moshi = Moshi.Builder().build()
    private val moshiConverterFactory = MoshiConverterFactory.create(moshi)
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofitClient = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(moshiConverterFactory)
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