package com.monoremix.redditapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.monoremix.myredditapp.model.Post
import com.monoremix.redditapp.api.RedditService
import com.monoremix.redditapp.db.RedditDatabase
import com.monoremix.redditapp.model.RemoteKeys
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RedditRemoteMediator @Inject constructor(
    private val service: RedditService,
    private val postDatabase: RedditDatabase
): RemoteMediator<Int, Post>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Post>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                /*remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.prevKey ?: null*/
                null
            }
            LoadType.PREPEND -> {
                /*remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey*/
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {

            val response = service.getTopPost(
                after = page,
                before = null
            )
            val posts = response.data.children.map { it.data}

            val endOfPaginationReached = posts.isEmpty()

            postDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    postDatabase.remoteKeysDao().clearRemoteKeys()
                    postDatabase.postsDao().clearPosts()
                }
                val nextKey = if (endOfPaginationReached) null else response.data.after
                val keys = posts.map {
                    RemoteKeys(postId = it.id, prevKey = null, nextKey = nextKey)
                }
                postDatabase.remoteKeysDao().insertAll(keys)
                postDatabase.postsDao().insertAll(posts)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        }catch (exception: IOException) {
            return MediatorResult.Error(exception)
        }catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Post>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                postDatabase.remoteKeysDao().remoteKeysPostId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Post>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { post ->
                postDatabase.remoteKeysDao().remoteKeysPostId(post.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Post>): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position->
            state.closestItemToPosition(position)?.id?.let { postId ->
                postDatabase.remoteKeysDao().remoteKeysPostId(postId)
            }
        }
    }
}

