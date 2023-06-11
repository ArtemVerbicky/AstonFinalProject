package ru.verb.astonfinalproject.data.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.verb.astonfinalproject.data.database.AppDb
import ru.verb.astonfinalproject.data.database.dao.CharacterDao
import ru.verb.astonfinalproject.data.database.dao.EpisodeDao
import ru.verb.astonfinalproject.data.database.dao.LocationDao
import javax.inject.Singleton

@Module(includes = [DaoModule::class])
class DataBaseModule {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDb {
        return Room.databaseBuilder(context, AppDb::class.java, "app.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}

@Module
class DaoModule {
    @Singleton
    @Provides
    fun provideCharacterDao(appDb: AppDb): CharacterDao {
        return appDb.characterDao()
    }

    @Singleton
    @Provides
    fun provideEpisodeDao(appDb: AppDb): EpisodeDao {
        return appDb.episodeDao()
    }

    @Singleton
    @Provides
    fun provideLocationDao(appDb: AppDb): LocationDao {
        return appDb.locationDao()
    }
}