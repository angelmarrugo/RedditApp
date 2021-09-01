package com.monoremix.redditapp.ui.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import com.monoremix.redditapp.R
import com.monoremix.redditapp.databinding.ItemPostBinding
import com.monoremix.redditapp.model.Children
import com.monoremix.redditapp.model.Post
import kotlin.math.ln
import kotlin.math.pow


class PostsAdapter: PagingDataAdapter<Post, PostsAdapter.PostViewHolder>(POST_COMPARATOR) {

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.create(parent)
    }


    companion object {
        private val POST_COMPARATOR = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem

        }
    }



    /**
     * [PostViewHolder]
     */

    class PostViewHolder(
        private val binding: ItemPostBinding
    ): RecyclerView.ViewHolder(binding.root) {

        private var _post: Children? = null

        init {

        }

        fun bind(post: Post?) {
            if (post == null) {
                TODO()
            } else {
                showPostData(post)
            }
        }

        @SuppressLint("SetTextI18n")
        private fun showPostData(post: Post) = with(binding) {

            val ago = DateUtils.getRelativeTimeSpanString(
                post.created!!*1000,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS)

            tvAuthor.text = post.subreddit_name_prefixed
            tvDescription.text = "Posted by u/${post.author} • $ago • ${post.domain}"
            tvAwards.text = "${post.total_awards_received} Awards"
            tvTitle.text = post.title
            tvVotes.text = withSuffix(post.ups!!.toLong())
            btnComments.text = withSuffix(post.num_comments!!.toLong())
            when(post.post_hint) {
                "image" -> {
                    ivPost.load(post.url_overridden_by_dest) {
                        crossfade(true)
                        crossfade(1000)
                    }
                }
                "hosted:video", "link" -> {
                    ivPost.load(post.thumbnail) {
                        crossfade(true)
                        crossfade(1000)
                    }
                }
                "self" -> {
                    contentMedia.visibility = View.GONE
                }
                else ->{
                    ivPost.clear()
                }
            }
        }

        companion object {
            fun create(parent: ViewGroup): PostViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_post, parent, false)
                return PostViewHolder(ItemPostBinding.bind(view))
            }
        }

        fun withSuffix(count: Long): String? {
            if (count < 1000) return "" + count
            val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
            return String.format(
                "%.1f %c",
                count / 1000.0.pow(exp.toDouble()),
                "kMGTPE"[exp - 1]
            )
        }

    }
}