package ru.verb.astonfinalproject.utils

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

const val ITEM_ID = "ITEM_ID"

fun Fragment.getItemsIds(items: List<String>): List<Int> {
    val ids = mutableListOf<Int>()

    for (item in items) {
        val dividedItem = item.split("/")
        ids.add(dividedItem[dividedItem.size - 1].toInt())
    }

    return ids
}

fun Fragment.getItemId(item: String): Bundle {
    val itemArray = item.split("/")
    val itemId = itemArray[itemArray.size - 1]

    return bundleOf(ITEM_ID to itemId.toInt())
}

fun Fragment.provideTextWatcher(scope: CoroutineScope,  action: suspend (CharSequence?) -> Unit) =
    object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            scope.launch {
                flowOf(s)
                    .debounce(500)
                    .collect {
                        action.invoke(it)
                    }
            }
        }

        override fun afterTextChanged(s: Editable?) {}

    }