package com.monoremix.redditapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.monoremix.redditapp.model.RemoteKeys

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("""
        SELECT *
        FROM REMOTE_KEYS
        WHERE postId = :postId
    """)
    suspend fun remoteKeysPostId(postId: String): RemoteKeys?

    @Query("""
        DELETE
        FROM REMOTE_KEYS
    """)
    suspend fun clearRemoteKeys()
}