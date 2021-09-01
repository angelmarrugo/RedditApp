package com.monoremix.redditapp.api


import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {
    @GET("top.json")
    suspend fun getTopPost(
        @Query("after") after: String?,
        @Query("before") before: String?,
    ): TopPostResponse

}