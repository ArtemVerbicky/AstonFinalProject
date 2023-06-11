package ru.verb.astonfinalproject.presentation.activities

import androidx.fragment.app.Fragment

val Fragment.selectedItemController: SelectedItemController
    get() = requireActivity() as SelectedItemController

interface SelectedItemController {
    fun setSelectedItem(itemId: Int)
}