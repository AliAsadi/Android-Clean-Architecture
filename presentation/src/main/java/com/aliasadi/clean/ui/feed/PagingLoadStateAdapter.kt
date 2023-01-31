package com.aliasadi.clean.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aliasadi.clean.databinding.ItemLoadStateFooterBinding

/**
 * @author by Ali Asadi on 31/01/2023
 */
class PagingLoadStateAdapter(
    private val onRetryClicked: () -> Unit
) : LoadStateAdapter<PagingLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(parent, onRetryClicked)


    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class LoadStateViewHolder(
        parent: ViewGroup,
        onRetryClicked: () -> Unit
    ) : RecyclerView.ViewHolder(
        ItemLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
    ) {

        private val binding = ItemLoadStateFooterBinding.bind(itemView)

        init {
            binding.buttonRetry.setOnClickListener { onRetryClicked() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            progressBar.isVisible = loadState is LoadState.Loading
            buttonRetry.isVisible = loadState !is LoadState.Loading
        }
    }
}