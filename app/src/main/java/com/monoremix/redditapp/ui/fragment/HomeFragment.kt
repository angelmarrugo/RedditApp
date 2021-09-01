package com.monoremix.redditapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.monoremix.redditapp.ui.adapter.PagerCollectionAdapter
import com.monoremix.redditapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterFragments: PagerCollectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initViewPager()
    }

    private fun FragmentHomeBinding.initViewPager() {
        adapterFragments = PagerCollectionAdapter(this@HomeFragment)
        pager.adapter = adapterFragments
        // attach tabLayout
        TabLayoutMediator(tabLayout, pager) {tab, position ->
            tab.text =  adapterFragments.getTitle(position)
        }.attach()
    }

}