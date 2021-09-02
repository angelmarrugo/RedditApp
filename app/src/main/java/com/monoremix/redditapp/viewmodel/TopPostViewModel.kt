package com.monoremix.myredditapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.monoremix.myredditapp.model.Post
import com.monoremix.redditapp.data.RedditRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TopPostViewModel @Inject constructor(
    private val repository: RedditRepository
): ViewModel() {

    private var currentResult: Flow<PagingData<Post>>? = null

    fun getTopPosts(): Flow<PagingData<Post>> {
        val lastResult = currentResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult = repository.getTopPostResultStream()
            .cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }

    override fun onCleared() {
        Log.d(TopPostViewModel::class.java.simpleName, "onCleared")
        super.onCleared()
    }
}