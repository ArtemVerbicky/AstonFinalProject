package ru.verb.astonfinalproject.presentation.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import ru.verb.astonfinalproject.data.repository.CharacterRepositoryImpl
import ru.verb.astonfinalproject.domain.CharacterRepository
import ru.verb.astonfinalproject.domain.models.Character
import ru.verb.astonfinalproject.error.ApiError
import ru.verb.astonfinalproject.error.NetworkError
import ru.verb.astonfinalproject.error.UnknownError

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class CharacterViewModel : ViewModel() {
    private val repository: CharacterRepository = CharacterRepositoryImpl()
    //буду использовать для фильтрации
    private val searchBy = MutableLiveData<String>()
    val data: Flow<PagingData<Character>>

    init {
       data = searchBy.asFlow()
           .debounce(500)
           .flatMapLatest {
               repository.getPagedCharacters()
           }
           .cachedIn(viewModelScope)
    }
}