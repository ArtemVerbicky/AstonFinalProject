package ru.verb.astonfinalproject.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class BaseFragment<VB: ViewBinding>: Fragment() {
    private var _binding: VB? = null
    val binding: VB? get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = createBinding()
        return binding?.root
    }
    abstract fun createBinding(): VB

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}