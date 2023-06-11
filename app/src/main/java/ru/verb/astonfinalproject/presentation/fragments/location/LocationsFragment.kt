package ru.verb.astonfinalproject.presentation.fragments.location


import android.content.Context
import android.os.Bundle
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
import ru.verb.astonfinalproject.databinding.FragmentLocationsBinding
import ru.verb.astonfinalproject.domain.models.Location
import ru.verb.astonfinalproject.presentation.GridSpacingItemDecoration
import ru.verb.astonfinalproject.presentation.activities.navigator
import ru.verb.astonfinalproject.presentation.activities.selectedItemController
import ru.verb.astonfinalproject.presentation.adapter.DefaultLoadStateAdapter
import ru.verb.astonfinalproject.presentation.adapter.location.LocationAdapter
import ru.verb.astonfinalproject.presentation.application.appComponent
import ru.verb.astonfinalproject.presentation.viewmodels.LocationViewModel
import ru.verb.astonfinalproject.presentation.viewmodels.di.LocationViewModelFactory
import ru.verb.astonfinalproject.utils.ITEM_ID
import ru.verb.astonfinalproject.utils.provideTextWatcher
import javax.inject.Inject

class LocationsFragment : BaseFragment<FragmentLocationsBinding>() {
    @Inject
    @LocationViewModelFactory
    lateinit var locationViewModelFactory: Lazy<ViewModelProvider.Factory>

    private val viewModel: LocationViewModel by viewModels {
        locationViewModelFactory.get()
    }

    override fun createBinding(): FragmentLocationsBinding {
        return FragmentLocationsBinding.inflate(requireActivity().layoutInflater)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.applicationContext.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        selectedItemController.setSelectedItem(R.id.locations)
        setupRecyclerView()
        setupSwipeRefresh()

        observeState()

        binding?.locationSearchEdit?.addTextChangedListener(provideTextWatcher(viewLifecycleOwner.lifecycleScope) { charSequence ->
            charSequence?.let {
                viewModel.locationPagingData.collectLatest { pagedLocations ->
                    pagedLocations.filter { location ->
                        location.name.contains(charSequence, ignoreCase = true)
                    }.apply {
                        (binding?.locationsList?.adapter as LocationAdapter).submitData(this)
                    }
                }
            }
        })

        lifecycleScope.launchWhenCreated {
            viewModel.locationPagingData.collect {
                (binding?.locationsList?.adapter as? LocationAdapter)?.submitData(it)
            }
        }
    }

    private fun setupRecyclerView() {
        binding?.locationsList?.layoutManager = GridLayoutManager(requireContext(), 2)
        binding?.locationsList?.addItemDecoration(GridSpacingItemDecoration(2, 8, true))

        val locationAdapter = LocationAdapter {
            toLocationDetailsFragment(it)
        }
        locationAdapter.withLoadStateFooter(DefaultLoadStateAdapter {
            locationAdapter.refresh()
        })
        binding?.locationsList?.adapter = locationAdapter
    }

    private fun setupSwipeRefresh() {
        binding?.swipeRefresh?.setOnRefreshListener {
            (binding?.locationsList?.adapter as LocationAdapter).refresh()
        }
    }

    private fun observeState() {
        binding?.let {
            val loadStateHolder = DefaultLoadStateAdapter.Holder(
                it.locationLoadingState,
                it.swipeRefresh,
            ) {
                (binding?.locationsList?.adapter as LocationAdapter).refresh()
            }

            (binding?.locationsList?.adapter as LocationAdapter).loadStateFlow
                .debounce(300)
                .onEach { loadState ->
                    loadStateHolder.bind(loadState.refresh)
                }
                .launchIn(lifecycleScope)
        }
    }

    private fun toLocationDetailsFragment(location: Location) {
        navigator.launchScreen(LocationDetailsFragment().apply {
            arguments = bundleOf(ITEM_ID to location.id)
        })
    }
}
