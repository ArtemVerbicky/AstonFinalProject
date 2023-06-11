package ru.verb.astonfinalproject.data.network.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.verb.astonfinalproject.data.network.api.CharacterApi
import ru.verb.astonfinalproject.data.network.api.EpisodeApi
import ru.verb.astonfinalproject.data.network.api.LocationApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val BASE_URL = "https://rickandmortyapi.com/api/"

@Module(includes = [ApiModule::class])
class NetworkModule {
    @Provides
    fun provideRetrofitClient(
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    fun provideMoshi(): Moshi {
        val moshiBuilder = Moshi.Builder()
        return moshiBuilder.build()
    }

    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()
}

@Module
class ApiModule {
    @Singleton
    @Provides
    fun provideCharacterApi(retrofit: Retrofit): CharacterApi {
        return retrofit.create(CharacterApi::class.java)
    }

    @Singleton
    @Provides
    fun provideEpisodeApi(retrofit: Retrofit): EpisodeApi {
        return retrofit.create(EpisodeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLocationApi(retrofit: Retrofit): LocationApi {
        return retrofit.create(LocationApi::class.java)
    }
}