package ru.verb.astonfinalproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.verb.astonfinalproject.databinding.CardLoadStateBinding

typealias TryAgainAction =  () -> Unit

class DefaultLoadStateAdapter(private val tryAgainAction: TryAgainAction): LoadStateAdapter<DefaultLoadStateAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }


    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        val binding = CardLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, null, tryAgainAction)
    }

    class Holder(
        private val binding: CardLoadStateBinding,
        private val swipeRefreshLayout: SwipeRefreshLayout?,
        private val tryAgainAction: TryAgainAction
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tryAgainButton.setOnClickListener {
                tryAgainAction.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                messageTextView.isVisible = loadState is LoadState.Error
                tryAgainButton.isVisible = loadState is LoadState.Error
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.isRefreshing = loadState is LoadState.Loading
                    progressBar.isVisible = false
                } else {
                    progressBar.isVisible = loadState is LoadState.Loading
                }
            }
        }

    }
}