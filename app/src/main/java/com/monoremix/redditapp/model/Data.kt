package com.monoremix.redditapp.model

import com.monoremix.redditapp.model.Children

data class Data(
    val after: String,
    val before: String?,
    val children: List<Children>,
    val dist: Int,
    val geo_filter: String,
    val modhash: String
)