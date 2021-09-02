package com.monoremix.redditapp.model

import com.monoremix.myredditapp.model.Post
import java.lang.Exception

sealed class TopPostResult {
    data class Success(val data: List<Post>): TopPostResult()
    data class Error(val error: Exception): TopPostResult()
}