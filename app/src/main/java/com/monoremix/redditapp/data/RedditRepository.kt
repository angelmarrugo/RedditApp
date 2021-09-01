package com.monoremix.redditapp.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.monoremix.redditapp.api.RedditService
import com.monoremix.redditapp.db.RedditDatabase
import com.monoremix.redditapp.model.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RedditRepository @Inject constructor(
    private val service: RedditService,
    private val database: RedditDatabase
) {

    fun getTopPostResultStream(): Flow<PagingData<Post>> {
        Log.d("RedditRepository", "new Stream")
        val pagingSourceFactory = { database.postsDao().retrievePosts() }
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = RedditRemoteMediator(
                service = service,
                postDatabase = database
            )
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 6
    }
}