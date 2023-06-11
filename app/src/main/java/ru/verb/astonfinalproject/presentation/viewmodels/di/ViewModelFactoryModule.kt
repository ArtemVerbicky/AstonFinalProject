package ru.verb.astonfinalproject.presentation.viewmodels.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.verb.astonfinalproject.presentation.viewmodels.CharacterViewModel
import ru.verb.astonfinalproject.presentation.viewmodels.EpisodeViewModel
import ru.verb.astonfinalproject.presentation.viewmodels.LocationViewModel
import javax.inject.Qualifier

@Module
interface ViewModelFactoryModule {
    @Binds
    @CharacterViewModelFactory
    fun provideCharacterViewModelFactory(
        characterViewModelFactory: CharacterViewModel.CharacterViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @LocationViewModelFactory
    fun provideLocationViewModelFactory(
        locationViewModelFactory: LocationViewModel.LocationViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @EpisodeViewModelFactory
    fun provideEpisodeViewModelFactory(
        episodeViewModelFactory: EpisodeViewModel.EpisodeViewModelFactory
    ): ViewModelProvider.Factory
}

@Qualifier
annotation class CharacterViewModelFactory

@Qualifier
annotation class LocationViewModelFactory

@Qualifier
annotation class EpisodeViewModelFactory