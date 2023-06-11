package ru.verb.astonfinalproject.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.verb.astonfinalproject.domain.CharacterRepository
import ru.verb.astonfinalproject.domain.models.Character
import javax.inject.Inject

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {
    val characterPagingData: Flow<PagingData<Character>> = repository.getPagedItems()
        .cachedIn(viewModelScope)

    val multipleCharactersData: StateFlow<List<Character>?> = repository.items
    val characterData: StateFlow<Character?> = repository.item

    fun getCharacter(id: Int) = viewModelScope.launch {
        repository.getItem(id)
    }

    fun getMultipleCharacters(ids: List<Int>) = viewModelScope.launch {
        repository.getMultipleItems(ids)
    }

    @Suppress("UNCHECKED_CAST")
    class CharacterViewModelFactory @Inject constructor(
        private val repository: CharacterRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            require(modelClass == CharacterViewModel::class)
            return CharacterViewModel(repository) as T
        }
    }
}