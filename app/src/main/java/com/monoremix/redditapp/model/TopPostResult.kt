package com.monoremix.redditapp.model

import java.lang.Exception

sealed class TopPostResult {
    data class Success(val data: List<Post>): TopPostResult()
    data class Error(val error: Exception): TopPostResult()
}