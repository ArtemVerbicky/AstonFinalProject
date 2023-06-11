package ru.verb.astonfinalproject.presentation.fragments.location

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import ru.verb.astonfinalproject.R
import ru.verb.astonfinalproject.core.BaseFragment
import ru.verb.astonfinalproject.databinding.FragmentLocationDetailsBinding
import ru.verb.astonfinalproject.domain.models.Character
import ru.verb.astonfinalproject.domain.models.Location
import ru.verb.astonfinalproject.presentation.GridSpacingItemDecoration
import ru.verb.astonfinalproject.presentation.activities.navigator
import ru.verb.astonfinalproject.presentation.adapter.character.CharacterInEpisodeDetailsAdapter
import ru.verb.astonfinalproject.presentation.application.appComponent
import ru.verb.astonfinalproject.presentation.fragments.HasBackArrow
import ru.verb.astonfinalproject.presentation.fragments.character.CharacterDetailsFragment
import ru.verb.astonfinalproject.presentation.fragments.character.CharactersFragment.Companion.CHARACTER_KEY
import ru.verb.astonfinalproject.presentation.viewmodels.CharacterViewModel
import ru.verb.astonfinalproject.presentation.viewmodels.LocationViewModel
import ru.verb.astonfinalproject.utils.ITEM_ID
import ru.verb.astonfinalproject.utils.getItemsIds
import javax.inject.Inject
import dagger.Lazy
import ru.verb.astonfinalproject.presentation.viewmodels.di.CharacterViewModelFactory
import ru.verb.astonfinalproject.presentation.viewmodels.di.LocationViewModelFactory

class LocationDetailsFragment : BaseFragment<FragmentLocationDetailsBinding>(), HasBackArrow {
    @Inject
    @LocationViewModelFactory
    lateinit var locationViewModelFactory: Lazy<ViewModelProvider.Factory>

    @Inject
    @CharacterViewModelFactory
    lateinit var characterViewModelFactory: Lazy<ViewModelProvider.Factory>

    private val locationViewModel: LocationViewModel by viewModels {
        locationViewModelFactory.get()
    }
    private val characterViewModel: CharacterViewModel by viewModels {
        characterViewModelFactory.get()
    }

    override fun createBinding(): FragmentLocationDetailsBinding {
        return FragmentLocationDetailsBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.applicationContext.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val locationId = requireArguments().getInt(ITEM_ID)

        locationViewModel.getLocation(locationId)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            locationViewModel.locationData.collect {
                it?.let {
                    prepareUi(it)
                    val charactersIds = getItemsIds(it.residents)
                    characterViewModel.getMultipleCharacters(charactersIds)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            characterViewModel.multipleCharactersData.collect {
                (binding?.episodeDetailsCharacters1List?.adapter as? CharacterInEpisodeDetailsAdapter)
                    ?.submitList(it)
            }
        }

    }

    private fun prepareUi(location: Location) {
        binding?.let {
            with(it) {
                locationDetailsName.text = getString(R.string.location_name, location.name)
                locationDetailsType.text = getString(R.string.location_type, location.type)
                locationDetailsDimension.text =
                    getString(R.string.location_dimension, location.dimension)
                setupRecyclerView()
            }
        }
    }

    private fun setupRecyclerView() {
        binding?.let {
            with(it) {
                episodeDetailsCharacters1List.layoutManager = GridLayoutManager(requireContext(), 2)
                episodeDetailsCharacters1List.addItemDecoration(
                    GridSpacingItemDecoration(
                        2,
                        8,
                        true
                    )
                )
                episodeDetailsCharacters1List.adapter =
                    CharacterInEpisodeDetailsAdapter { character ->
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