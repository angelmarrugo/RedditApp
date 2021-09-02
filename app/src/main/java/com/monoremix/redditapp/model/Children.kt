package com.monoremix.redditapp.model

import com.monoremix.myredditapp.model.Post

data class Children(
    val `data`: Post,
    val kind: String
)