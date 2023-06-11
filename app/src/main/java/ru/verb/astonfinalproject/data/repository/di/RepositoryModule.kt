package ru.verb.astonfinalproject.data.repository.di

import dagger.Binds
import dagger.Module
import ru.verb.astonfinalproject.data.repository.CharacterRepositoryImpl
import ru.verb.astonfinalproject.data.repository.EpisodeRepositoryImpl
import ru.verb.astonfinalproject.data.repository.LocationRepositoryImpl
import ru.verb.astonfinalproject.domain.CharacterRepository
import ru.verb.astonfinalproject.domain.EpisodeRepository
import ru.verb.astonfinalproject.domain.LocationRepository

@Module
interface RepositoryModule {
    @Binds
    fun provideCharacterRepository(characterRepositoryImpl: CharacterRepositoryImpl): CharacterRepository

    @Binds
    fun provideLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository

    @Binds
    fun provideEpisodeRepository(episodeRepositoryImpl: EpisodeRepositoryImpl): EpisodeRepository
}