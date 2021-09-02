package com.monoremix.myredditapp.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts",
    indices = [
        Index("media_video_fallback_url")
    ])
data class Post(
    //val all_awardings: List<AllAwarding>,
    val allow_live_comments: Boolean,
    val author: String,
    val author_fullname: String,
    val author_is_blocked: Boolean?,
    val author_patreon_flair: Boolean?,
    val author_premium: Boolean?,
    val can_gild: Boolean?,
    val can_mod_post: Boolean?,
    val clicked: Boolean?,
    val contest_mode: Boolean?,
    val created: Double?,
    val created_utc: Double?,
    val domain: String?,
    val downs: Int?,
    val hide_score: Boolean?,
    @PrimaryKey
    val id: String,
    val is_created_from_ads_ui: Boolean?,
    val is_crosspostable: Boolean?,
    val is_meta: Boolean?,
    val is_original_content: Boolean?,
    val is_reddit_media_domain: Boolean?,
    val is_robot_indexable: Boolean?,
    val is_self: Boolean?,
    val is_video: Boolean?,
    val link_flair_background_color: String?,
    val link_flair_css_class: String?,
    val link_flair_template_id: String?,
    val link_flair_text: String?,
    val link_flair_text_color: String?,
    val link_flair_type: String?,
    val locked: Boolean?,
    @field:Embedded(prefix = "media_")
    val media: Media?,
    val media_only: Boolean?,
    val name: String?,
    val no_follow: Boolean?,
    val num_comments: Int?,
    val num_crossposts: Int?,
    val over_18: Boolean?,
    val parent_whitelist_status: String?,
    val permalink: String?,
    val pinned: Boolean?,
    val post_hint: String?,
    val pwls: Int?,
    val quarantine: Boolean?,
    val saved: Boolean?,
    val score: Int?,
    //val secure_media: SecureMedia,
    val selftext: String?,
    val send_replies: Boolean?,
    val spoiler: Boolean?,
    val stickied: Boolean?,
    val subreddit: String?,
    val subreddit_id: String?,
    val subreddit_name_prefixed: String?,
    val subreddit_subscribers: Int?,
    val subreddit_type: String?,
    val thumbnail: String?,
    val thumbnail_height: Int?,
    val thumbnail_width: Int?,
    val title: String?,
    val total_awards_received: Int?,
    val ups: Int?,
    val upvote_ratio: Double?,
    val url: String?,
    val url_overridden_by_dest: String?,
    val visited: Boolean?,
    val whitelist_status: String?,
    val wls: Int?
){
    data class Media(
        @field:Embedded(prefix = "video_")
        val reddit_video: RedditVideo?
    ) {
        data class RedditVideo(
            val bitrate_kbps: Int,
            val dash_url: String,
            val duration: Int,
            val fallback_url: String,
            val height: Int,
            val hls_url: String,
            val is_gif: Boolean,
            val scrubber_media_url: String,
            val transcoding_status: String,
            val width: Int
        )
    }
}