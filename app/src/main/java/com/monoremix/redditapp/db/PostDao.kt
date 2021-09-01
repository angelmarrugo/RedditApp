package com.monoremix.redditapp.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.monoremix.redditapp.model.Post

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<Post>)

    @Query("""
        SELECT *
        FROM POSTS
    """)
    fun retrievePosts(): PagingSource<Int, Post>

    @Query("""
        DELETE
        FROM POSTS
    """)
    suspend fun clearPosts()
}