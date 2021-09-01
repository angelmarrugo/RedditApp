package com.monoremix.redditapp.model

import com.monoremix.redditapp.model.Data

data class Response(
    val `data`: Data,
    val kind: String
)