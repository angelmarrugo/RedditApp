package com.monoremix.redditapp.model

data class AllAwarding(
    val award_sub_type: String,
    val award_type: String,
    val coin_price: Int,
    val coin_reward: Int,
    val count: Int,
    val days_of_drip_extension: Int,
    val days_of_premium: Int,
    val description: String,
    val icon_height: Int,
    val icon_url: String,
    val icon_width: Int,
    val id: String,
    val is_enabled: Boolean,
    val is_new: Boolean,
    val name: String,
    //val resized_icons: List<ResizedIcon>,
    val static_icon_height: Int,
    val static_icon_url: String,
    val static_icon_width: Int,
    val subreddit_coin_reward: Int,
)