package ru.verb.astonfinalproject.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.flatMap
import kotlinx.coroutines.flow.collectLatest
import ru.verb.astonfinalproject.core.BaseFragment
import ru.verb.astonfinalproject.databinding.FragmentCharactersBinding
import ru.verb.astonfinalproject.network.ConnectionService
import ru.verb.astonfinalproject.presentation.adapter.CharacterAdapter
import ru.verb.astonfinalproject.presentation.viewmodels.CharacterViewModel

class CharactersFragment : BaseFragment<FragmentCharactersBinding>() {
    private val viewModel: CharacterViewModel by viewModels()
    override fun createBinding(): FragmentCharactersBinding {
        return FragmentCharactersBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = CharacterAdapter {

        }
        binding?.charactersList?.adapter = adapter
        lifecycleScope.launchWhenCreated {
            val list = ConnectionService.CharacterApiService.service.getPagedItems(1)
            viewModel.data.collectLatest { data ->
                adapter.submitData(data)
            }
        }
    }


}