package com.monoremix.redditapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.monoremix.redditapp.ui.adapter.PostsAdapter
import com.monoremix.redditapp.ui.adapter.PostsLoadStateAdapter
import com.monoremix.myredditapp.viewmodel.TopPostViewModel
import com.monoremix.redditapp.R
import com.monoremix.redditapp.databinding.FragmentTopPostBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopPostFragment : Fragment() {

    private lateinit var binding: FragmentTopPostBinding
    private val topPostViewModel: TopPostViewModel by viewModels()
    private val postsAdapter: PostsAdapter by lazy { PostsAdapter() }

    private var postsJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopPostBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.initAdapter()

        retrievePosts()

        // listeners
        binding.viewLoad.retryButton.setOnClickListener { postsAdapter.retry() }
        binding.swipeRefresh.setOnRefreshListener { postsAdapter.refresh() }

    }

    private fun retrievePosts() {
        // Make sure we cancel the previous job before creating a new one
        postsJob?.cancel()
        postsJob = lifecycleScope.launch {
            topPostViewModel.getTopPosts().collectLatest {
                postsAdapter.submitData(it)
            }
        }
    }

    private fun FragmentTopPostBinding.initAdapter() {
        lvPosts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postsAdapter.withLoadStateHeaderAndFooter(
                header = PostsLoadStateAdapter{ postsAdapter.retry() },
                footer = PostsLoadStateAdapter{ postsAdapter.retry() }
            )
            setHasFixedSize(true)
        }

        lifecycleScope.launch {
            postsAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { lvPosts.scrollToPosition(0) }
        }

        postsAdapter.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty = loadState.refresh is LoadState.NotLoading && postsAdapter.itemCount == 0
            showEmptyList(isListEmpty)

            // Only show the list if refresh succeeds, either from the the local db or the remote.
            val refresh = loadState.source.refresh is LoadState.NotLoading
                    || loadState.mediator?.refresh is LoadState.NotLoading
            lvPosts.isVisible = refresh

            // Show loading spinner during initial load or refresh.
            viewLoad.progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading && postsAdapter.itemCount == 0
            // Show the retry state if initial load or refresh fails.
            viewLoad.retryButton.isVisible = loadState.mediator?.refresh is LoadState.Error && postsAdapter.itemCount == 0
            //
            viewLoad.errorMsg.isVisible = loadState.mediator?.refresh is LoadState.Error && postsAdapter.itemCount == 0
            // Hide when refresh its over
            binding.swipeRefresh.isRefreshing = loadState.mediator?.refresh is LoadState.Loading && postsAdapter.itemCount > 0
            if (loadState.mediator?.refresh is LoadState.Error) {
                val snackbar: Snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_LONG)
                val customView = layoutInflater.inflate(R.layout.view_error, null)
                val snackBarLayout: Snackbar.SnackbarLayout = snackbar.view as Snackbar.SnackbarLayout
                snackBarLayout.setPadding(0,0,0,0)
                snackBarLayout.addView(customView)
                snackbar.show()
            }
            //Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    context,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun FragmentTopPostBinding.showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            lvPosts.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            lvPosts.visibility = View.VISIBLE
        }
    }

}