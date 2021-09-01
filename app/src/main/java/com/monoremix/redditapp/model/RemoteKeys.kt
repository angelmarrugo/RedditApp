package com.monoremix.redditapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val postId: String,
    val prevKey: String?,
    val nextKey: String?
)
