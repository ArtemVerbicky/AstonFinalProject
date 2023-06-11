package ru.verb.astonfinalproject.presentation.activities

import androidx.fragment.app.Fragment

val Fragment.navigator: Navigator
    get() = requireActivity() as Navigator

interface Navigator {
    fun launchScreen(fragment: Fragment)
    fun goBack()
}