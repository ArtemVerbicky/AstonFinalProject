package ru.verb.astonfinalproject.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.verb.astonfinalproject.domain.LocationRepository
import ru.verb.astonfinalproject.domain.models.Location
import javax.inject.Inject

class LocationViewModel(
    private val repository: LocationRepository
) : ViewModel() {
    val locationPagingData: Flow<PagingData<Location>> = repository.getPagedItems()
        .cachedIn(viewModelScope)

    val multipleLocationsData: StateFlow<List<Location>?> = repository.items
    val locationData: StateFlow<Location?> = repository.item

    fun getLocation(id: Int) = viewModelScope.launch {
        repository.getItem(id)
    }

    fun getMultipleLocations(ids: List<Int>) = viewModelScope.launch {
        repository.getMultipleItems(ids)
    }

    class LocationViewModelFactory @Inject constructor(
        private val repository: LocationRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            //require(modelClass == LocationViewModel::class)
            return LocationViewModel(repository) as T
        }
    }
}