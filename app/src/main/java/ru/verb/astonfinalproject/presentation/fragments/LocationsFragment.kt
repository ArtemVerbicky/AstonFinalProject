package ru.verb.astonfinalproject.presentation.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import ru.verb.astonfinalproject.core.BaseFragment
import ru.verb.astonfinalproject.databinding.FragmentLocationsBinding
import ru.verb.astonfinalproject.network.ConnectionService
import ru.verb.astonfinalproject.presentation.adapter.LocationAdapter
import ru.verb.astonfinalproject.presentation.viewmodels.LocationViewModel

class LocationsFragment : BaseFragment<FragmentLocationsBinding>() {
    val viewModel: LocationViewModel by viewModels()
    override fun createBinding(): FragmentLocationsBinding {
        return FragmentLocationsBinding.inflate(requireActivity().layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = LocationAdapter {

        }
        binding?.locationsList?.adapter = adapter
        lifecycleScope.launchWhenCreated {
            viewModel.data.collectLatest {
                val list = ConnectionService.LocationApiService.service.getPagedItems(1)
                adapter.submitData(it)
            }
        }
    }

}