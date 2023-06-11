package ru.verb.astonfinalproject.presentation.fragments.character

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.Lazy
import ru.verb.astonfinalproject.R
import ru.verb.astonfinalproject.core.BaseFragment
import ru.verb.astonfinalproject.databinding.FragmentCharacterDetailsBinding
import ru.verb.astonfinalproject.domain.models.Character
import ru.verb.astonfinalproject.domain.models.Episode
import ru.verb.astonfinalproject.presentation.HorizontalLinearSpacingItemDecoration
import ru.verb.astonfinalproject.presentation.activities.navigator
import ru.verb.astonfinalproject.presentation.adapter.episode.EpisodeInLocationDetailsAdapter
import ru.verb.astonfinalproject.presentation.application.appComponent
import ru.verb.astonfinalproject.presentation.fragments.HasBackArrow
import ru.verb.astonfinalproject.presentation.fragments.character.CharactersFragment.Companion.CHARACTER_KEY
import ru.verb.astonfinalproject.presentation.fragments.episode.EpisodeDetailsFragment
import ru.verb.astonfinalproject.presentation.fragments.episode.EpisodesFragment.Companion.EPISODE_KEY
import ru.verb.astonfinalproject.presentation.fragments.location.LocationDetailsFragment
import ru.verb.astonfinalproject.presentation.viewmodels.EpisodeViewModel
import ru.verb.astonfinalproject.presentation.viewmodels.di.EpisodeViewModelFactory
import ru.verb.astonfinalproject.utils.getItemId
import ru.verb.astonfinalproject.utils.getItemsIds
import javax.inject.Inject


class CharacterDetailsFragment : BaseFragment<FragmentCharacterDetailsBinding>(), HasBackArrow {
    @Inject
    @EpisodeViewModelFactory
    lateinit var episodeViewModelFactory: Lazy<ViewModelProvider.Factory>

    private val episodeViewModel: EpisodeViewModel by viewModels {
        episodeViewModelFactory.get()
    }

    override fun createBinding(): FragmentCharacterDetailsBinding {
        return FragmentCharacterDetailsBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.applicationContext.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val character = if (Build.VERSION.SDK_INT < 33) {
            requireArguments().getParcelable<Character>(CHARACTER_KEY)
        } else {
            requireArguments().getParcelable(CHARACTER_KEY, Character::class.java)
        }

        character?.let {
            val episodesIds = getItemsIds(it.episode)
            episodeViewModel.getMultipleEpisodes(episodesIds)
            prepareUi(character)
        }

        lifecycleScope.launchWhenCreated {
            episodeViewModel.multipleEpisodesData.collect { episodes ->
                (binding?.characterDetailsEpisodesList?.adapter as? EpisodeInLocationDetailsAdapter)
                    ?.submitList(episodes)
            }
        }
    }

    private fun prepareUi(character: Character) {
        binding?.let {
            with(it) {
                characterDetailsName.text = getString(R.string.character_name, character.name)
                characterDetailsStatus.text = getString(R.string.character_status, character.status.name)
                characterDetailsSpecies.text = getString(R.string.character_species, character.species)
                characterDetailsGender.text = getString(R.string.character_gender, character.gender.name)
                characterDetailsOrigin.apply {
                    text = getString(R.string.character_origin, character.origin.name)
                    setOnClickListener {
                        val itemId = getItemId(character.origin.url)
                        toLocationDetailsFragment(itemId)
                    }
                }
                characterDetailsLocation.apply {
                    text = getString(R.string.character_location, character.characterLocation?.name)
                    val itemId = character.characterLocation?.url?.let { locationUrl ->
                        getItemId(locationUrl)
                    }
                    setOnClickListener {
                        itemId?.let { id ->
                            toLocationDetailsFragment(id)
                        }
                    }
                }
            }
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding?.let {
            with(it) {
                characterDetailsEpisodesList.addItemDecoration(HorizontalLinearSpacingItemDecoration(16))
                characterDetailsEpisodesList.adapter = EpisodeInLocationDetailsAdapter { episode ->
                    toEpisodeDetailsFragment(episode)
                }
            }
        }
    }

    private fun toLocationDetailsFragment(args: Bundle) {
        val locationDetailsFragment = LocationDetailsFragment().apply { arguments = args }
        navigator.launchScreen(locationDetailsFragment)
    }

    private fun toEpisodeDetailsFragment(episode: Episode) {
        val episodeDetailsFragment = EpisodeDetailsFragment().apply {
            arguments = bundleOf(EPISODE_KEY to episode)
        }
        navigator.launchScreen(episodeDetailsFragment)
    }
}