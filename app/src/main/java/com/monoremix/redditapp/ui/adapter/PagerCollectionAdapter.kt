package com.monoremix.redditapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.monoremix.redditapp.ui.fragment.SocialFragment
import com.monoremix.redditapp.ui.fragment.TopPostFragment


class PagerCollectionAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {



    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                TopPostFragment()
            }
            1 -> {
                SocialFragment()
            }
            else -> {
                Fragment()
            }
        }
    }

    fun getTitle(position: Int): String {
        return when (position) {
            0 -> {
                "Popular"
            }
            1 -> {
                "Social"
            }
            else -> {
                "Default"
            }
        }
    }
}