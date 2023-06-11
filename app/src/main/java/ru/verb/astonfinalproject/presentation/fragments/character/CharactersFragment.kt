package ru.verb.astonfinalproject.presentation.fragments.character

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.filter
import androidx.recyclerview.widget.GridLayoutManager
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.verb.astonfinalproject.R
import ru.verb.astonfinalproject.core.BaseFragment
import ru.verb.astonfinalproject.databinding.FragmentCharactersBinding
import ru.verb.astonfinalproject.presentation.GridSpacingItemDecoration
import ru.verb.astonfinalproject.presentation.activities.navigator
import ru.verb.astonfinalproject.presentation.activities.selectedItemController
import ru.verb.astonfinalproject.presentation.adapter.DefaultLoadStateAdapter
import ru.verb.astonfinalproject.presentation.adapter.character.CharacterAdapter
import ru.verb.astonfinalproject.presentation.application.appComponent
import ru.verb.astonfinalproject.presentation.viewmodels.CharacterViewModel
import ru.verb.astonfinalproject.presentation.viewmodels.di.CharacterViewModelFactory
import ru.verb.astonfinalproject.utils.provideTextWatcher
import javax.inject.Inject

class CharactersFragment : BaseFragment<FragmentCharactersBinding>() {
    @Inject
    @CharacterViewModelFactory
    lateinit var characterViewModelFactory: Lazy<ViewModelProvider.Factory>

    private val viewModel: CharacterViewModel by viewModels {
        characterViewModelFactory.get()
    }


    companion object {
        const val CHARACTER_KEY = "CHARACTER_KEY"
    }

    override fun createBinding(): FragmentCharactersBinding {
        return FragmentCharactersBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.applicationContext.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        selectedItemController.setSelectedItem(R.id.characters)
        setupRecyclerView()
        setupSwipeRefresh()

        observeState()

        binding?.characterSearchEdit?.addTextChangedListener(provideTextWatcher(viewLifecycleOwner.lifecycleScope) { charSequence ->
            charSequence?.let {
                viewModel.characterPagingData.collectLatest { pagedCharacters ->
                    pagedCharacters.filter { character ->
                        character.name.contains(charSequence, ignoreCase = true)
                    }.apply {
                        (binding?.charactersList?.adapter as CharacterAdapter).submitData(this)
                    }
                }
            }
        })

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.characterPagingData.collect {
                (binding?.charactersList?.adapter as? CharacterAdapter)?.submitData(it)
            }
        }
    }

    private fun setupAdapter() {
        val characterAdapter = CharacterAdapter { character ->
            navigator.launchScreen(CharacterDetailsFragment().apply {
                arguments = bundleOf(CHARACTER_KEY to character)
            })
        }
        characterAdapter.withLoadStateFooter(DefaultLoadStateAdapter {
            characterAdapter.refresh()
        })
        binding?.charactersList?.adapter = characterAdapter
    }

    private fun setupRecyclerView() {
        setupAdapter()
        binding?.charactersList?.layoutManager = GridLayoutManager(requireContext(), 2)
        binding?.charactersList?.addItemDecoration(GridSpacingItemDecoration(2,8,true))
    }

    private fun setupSwipeRefresh() {
        binding?.swipeRefresh?.setOnRefreshListener {
            (binding?.charactersList?.adapter as CharacterAdapter).refresh()
        }
    }

    private fun observeState() {
        binding?.let {
            val loadStateHolder = DefaultLoadStateAdapter.Holder(
                it.characterLoadingState,
                it.swipeRefresh,
            ) {
                (binding?.charactersList?.adapter as CharacterAdapter).refresh()
            }

            (binding?.charactersList?.adapter as CharacterAdapter).loadStateFlow
                .debounce(300)
                .onEach { loadState ->
                    loadStateHolder.bind(loadState.refresh)
                }
                .launchIn(lifecycleScope)
        }
    }
}