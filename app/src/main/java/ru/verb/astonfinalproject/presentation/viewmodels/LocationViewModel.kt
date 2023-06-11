package ru.verb.astonfinalproject.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import ru.verb.astonfinalproject.data.repository.LocationRepositoryImpl
import ru.verb.astonfinalproject.domain.LocationRepository
import ru.verb.astonfinalproject.domain.models.Location

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class LocationViewModel: ViewModel() {
    private val repository: LocationRepository = LocationRepositoryImpl()
    //буду использовать для фильтрации
    private val searchBy = MutableLiveData<String>()
    val data: Flow<PagingData<Location>>

    init {
        data = searchBy.asFlow()
            .debounce(500)
            .flatMapLatest {
                repository.getPagedLocations()
            }
            .cachedIn(viewModelScope)
    }
}