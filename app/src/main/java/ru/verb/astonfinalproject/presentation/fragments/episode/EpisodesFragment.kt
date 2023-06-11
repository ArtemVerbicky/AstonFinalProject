package ru.verb.astonfinalproject.presentation.fragments.episode

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
import ru.verb.astonfinalproject.databinding.FragmentEpisodesBinding
import ru.verb.astonfinalproject.domain.models.Episode
import ru.verb.astonfinalproject.presentation.GridSpacingItemDecoration
import ru.verb.astonfinalproject.presentation.activities.navigator
import ru.verb.astonfinalproject.presentation.activities.selectedItemController
import ru.verb.astonfinalproject.presentation.adapter.DefaultLoadStateAdapter
import ru.verb.astonfinalproject.presentation.adapter.episode.EpisodeAdapter
import ru.verb.astonfinalproject.presentation.application.appComponent
import ru.verb.astonfinalproject.presentation.viewmodels.EpisodeViewModel
import ru.verb.astonfinalproject.presentation.viewmodels.di.EpisodeViewModelFactory
import ru.verb.astonfinalproject.utils.provideTextWatcher
import javax.inject.Inject

class EpisodesFragment : BaseFragment<FragmentEpisodesBinding>() {
    @Inject
    @EpisodeViewModelFactory
    lateinit var episodeViewModelFactory: Lazy<ViewModelProvider.Factory>

    private val viewModel: EpisodeViewModel by viewModels {
        episodeViewModelFactory.get()
    }

    companion object {
        const val EPISODE_KEY = "EPISODE_KEY"
    }

    override fun createBinding(): FragmentEpisodesBinding {
        return FragmentEpisodesBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.applicationContext.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        selectedItemController.setSelectedItem(R.id.episodes)
        setupRecyclerView()
        setupSwipeRefresh()

        observeState()

        binding?.episodeSearchEdit?.addTextChangedListener(provideTextWatcher(viewLifecycleOwner.lifecycleScope) { charSequence ->
            charSequence?.let {
                viewModel.episodePagingData.collectLatest { pagedEpisodes ->
                    pagedEpisodes.filter { episode ->
                        episode.name.contains(charSequence, ignoreCase = true)
                    }.apply {
                        (binding?.episodesList?.adapter as EpisodeAdapter).submitData(this)
                    }
                }
            }
        })

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.episodePagingData.collectLatest {
                (binding?.episodesList?.adapter as? EpisodeAdapter)?.submitData(it)
            }
        }
    }


    private fun setupRecyclerView() {
        binding?.episodesList?.layoutManager = GridLayoutManager(requireContext(), 2)
        binding?.episodesList?.addItemDecoration(GridSpacingItemDecoration(2, 8, true))

        val episodeAdapter = EpisodeAdapter { episode ->
            toEpisodeDetailsFragment(episode)
        }
        episodeAdapter.withLoadStateFooter(DefaultLoadStateAdapter {
            episodeAdapter.refresh()
        })
        binding?.episodesList?.adapter = episodeAdapter
    }

    private fun setupSwipeRefresh() {
        binding?.swipeRefresh?.setOnRefreshListener {
            (binding?.episodesList?.adapter as EpisodeAdapter).refresh()
        }
    }

    private fun observeState() {
        binding?.let {
            val loadStateHolder = DefaultLoadStateAdapter.Holder(
                it.episodeLoadingState,
                it.swipeRefresh,
            ) {
                (binding?.episodesList?.adapter as EpisodeAdapter).refresh()
            }

            (binding?.episodesList?.adapter as EpisodeAdapter).loadStateFlow
                .debounce(300)
                .onEach { loadState ->
                    loadStateHolder.bind(loadState.refresh)
                }
                .launchIn(lifecycleScope)
        }
    }

    private fun toEpisodeDetailsFragment(episode: Episode) {
        navigator.launchScreen(EpisodeDetailsFragment().apply {
            arguments = bundleOf(EPISODE_KEY to episode)
        })
    }
}