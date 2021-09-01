package com.monoremix.redditapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.monoremix.redditapp.R
import com.monoremix.redditapp.databinding.ItemPostsLoadStateFooterViewBinding

class PostsLoadStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<PostsLoadStateAdapter.PostsLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: PostsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PostsLoadStateViewHolder {
        return PostsLoadStateViewHolder.create(parent, retry)
    }



    /**
     * [PostsLoadStateViewHolder]
     */

    class PostsLoadStateViewHolder(
        private val binding: ItemPostsLoadStateFooterViewBinding,
        retry: () -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) = with(binding) {

            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): PostsLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_posts_load_state_footer_view, parent, false)
                val binding = ItemPostsLoadStateFooterViewBinding.bind(view)
                return PostsLoadStateViewHolder(binding, retry)
            }
        }

    }
}