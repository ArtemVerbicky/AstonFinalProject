package ru.verb.astonfinalproject.presentation.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import ru.verb.astonfinalproject.data.database.di.DataBaseModule
import ru.verb.astonfinalproject.data.network.di.NetworkModule
import ru.verb.astonfinalproject.data.repository.di.RepositoryModule
import ru.verb.astonfinalproject.presentation.activities.MainActivity
import ru.verb.astonfinalproject.presentation.fragments.character.CharacterDetailsFragment
import ru.verb.astonfinalproject.presentation.fragments.character.CharactersFragment
import ru.verb.astonfinalproject.presentation.fragments.episode.EpisodeDetailsFragment
import ru.verb.astonfinalproject.presentation.fragments.episode.EpisodesFragment
import ru.verb.astonfinalproject.presentation.fragments.location.LocationDetailsFragment
import ru.verb.astonfinalproject.presentation.fragments.location.LocationsFragment
import ru.verb.astonfinalproject.presentation.viewmodels.di.ViewModelFactoryModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: CharactersFragment)
    fun inject(fragment: CharacterDetailsFragment)
    fun inject(fragment: LocationsFragment)
    fun inject(fragment: LocationDetailsFragment)
    fun inject(fragment: EpisodesFragment)
    fun inject(fragment: EpisodeDetailsFragment)



    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}

@Module(includes = [
    DataBaseModule::class,
    NetworkModule::class,
    RepositoryModule::class,
    ViewModelFactoryModule::class
])
interface AppModule