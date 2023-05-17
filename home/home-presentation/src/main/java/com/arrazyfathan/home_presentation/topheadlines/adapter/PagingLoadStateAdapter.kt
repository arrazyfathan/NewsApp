package com.arrazyfathan.home_presentation.topheadlines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arrazyfathan.home_presentation.R
import com.arrazyfathan.home_presentation.databinding.ItemArticleLoadingStateBinding

/**
 * Created by Ar Razy Fathan Rabbani on 12/05/23.
 */
class PagingLoadStateAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    private val adapter: PagingDataAdapter<T, VH>
) : LoadStateAdapter<PagingLoadStateAdapter.NetworkStateItemViewHolder>() {

    class NetworkStateItemViewHolder(
        private val binding: ItemArticleLoadingStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnRetry.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                loadingContent.isVisible = loadState is LoadState.Loading
                errorLoadingContent.isVisible = loadState is LoadState.Error
                errorMsg.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                errorMsg.text = (loadState as? LoadState.Error)?.error?.message
            }
        }
    }

    override fun onBindViewHolder(
        holder: NetworkStateItemViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        return NetworkStateItemViewHolder(
            ItemArticleLoadingStateBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_article_loading_state, parent, false)
            )
        ) {
            adapter.retry()
        }
    }

}