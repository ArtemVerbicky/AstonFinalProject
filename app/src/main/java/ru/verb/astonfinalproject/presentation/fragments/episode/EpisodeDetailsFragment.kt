package ru.verb.astonfinalproject.presentation.fragments.episode

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.Lazy
import ru.verb.astonfinalproject.R
import ru.verb.astonfinalproject.core.BaseFragment
import ru.verb.astonfinalproject.databinding.FragmentEpisodeDetailsBinding
import ru.verb.astonfinalproject.domain.models.Character
import ru.verb.astonfinalproject.domain.models.Episode
import ru.verb.astonfinalproject.presentation.GridSpacingItemDecoration
import ru.verb.astonfinalproject.presentation.activities.navigator
import ru.verb.astonfinalproject.presentation.adapter.character.CharacterInEpisodeDetailsAdapter
import ru.verb.astonfinalproject.presentation.application.appComponent
import ru.verb.astonfinalproject.presentation.fragments.HasBackArrow
import ru.verb.astonfinalproject.presentation.fragments.character.CharacterDetailsFragment
import ru.verb.astonfinalproject.presentation.fragments.character.CharactersFragment.Companion.CHARACTER_KEY
import ru.verb.astonfinalproject.presentation.fragments.episode.EpisodesFragment.Companion.EPISODE_KEY
import ru.verb.astonfinalproject.presentation.viewmodels.CharacterViewModel
import ru.verb.astonfinalproject.presentation.viewmodels.di.CharacterViewModelFactory
import ru.verb.astonfinalproject.utils.getItemsIds
import javax.inject.Inject


class EpisodeDetailsFragment : BaseFragment<FragmentEpisodeDetailsBinding>(), HasBackArrow {
    @Inject
    @CharacterViewModelFactory
    lateinit var characterViewModelFactory: Lazy<ViewModelProvider.Factory>

    private val characterViewModel: CharacterViewModel by viewModels {
        characterViewModelFactory.get()
    }

    override fun createBinding(): FragmentEpisodeDetailsBinding {
        return FragmentEpisodeDetailsBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.applicationContext.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val episode = if (Build.VERSION.SDK_INT < 33) {
            requireArguments().getParcelable<Episode>(EPISODE_KEY)
        } else {
            requireArguments().getParcelable(EPISODE_KEY, Episode::class.java)
        }

        episode?.let {
            prepareUi(it)
            val charactersIds = getItemsIds(it.characters)
            characterViewModel.getMultipleCharacters(charactersIds)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            characterViewModel.multipleCharactersData.collect {
                (binding?.episodeDetailsCharacters1List?.adapter as? CharacterInEpisodeDetailsAdapter)
                    ?.submitList(it)
            }
        }
    }

    private fun prepareUi(episode: Episode) {
        binding?.let {
            with(it) {
                episodeDetailsName.text = getString(R.string.episode_name, episode.name)
                episodeDetailsAirDate.text = getString(R.string.episode_air_date_txt, episode.airDate)
                episodeDetailsEpisode.text = getString(R.string.episode, episode.episode)
                setupRecyclerView()
            }
        }
    }

    private fun setupRecyclerView() {
        binding?.let {
            with(it) {
                episodeDetailsCharacters1List.layoutManager = GridLayoutManager(requireContext(), 2)
                episodeDetailsCharacters1List.addItemDecoration(GridSpacingItemDecoration(2, 8, true))
                episodeDetailsCharacters1List.adapter = CharacterInEpisodeDetailsAdapter { character ->
                    toCharacterDetailsFragment(character)
                }
            }
        }
    }

    private fun toCharacterDetailsFragment(character: Character) {
        val characterDetailsFragment = CharacterDetailsFragment().apply {
            arguments = bundleOf(CHARACTER_KEY to character)
        }
        navigator.launchScreen(characterDetailsFragment)
    }
}