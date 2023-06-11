package ru.verb.astonfinalproject.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.verb.astonfinalproject.domain.EpisodeRepository
import ru.verb.astonfinalproject.domain.models.Episode
import javax.inject.Inject

class EpisodeViewModel(
    private val repository: EpisodeRepository
) : ViewModel() {
    val episodePagingData: Flow<PagingData<Episode>> = repository.getPagedItems()
        .cachedIn(viewModelScope)

    val multipleEpisodesData: StateFlow<List<Episode>?> = repository.items
    val episodeData: StateFlow<Episode?> = repository.item

    fun getEpisode(id: Int) = viewModelScope.launch {
        repository.getItem(id)
    }

    fun getMultipleEpisodes(ids: List<Int>) = viewModelScope.launch {
        repository.getMultipleItems(ids)
    }

    @Suppress("UNCHECKED_CAST")
    class EpisodeViewModelFactory @Inject constructor(
        private val repository: EpisodeRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            //require(modelClass == EpisodeViewModel::class)
            return EpisodeViewModel(repository) as T
        }
    }
}