package com.monoremix.redditapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monoremix.redditapp.R
import com.monoremix.redditapp.databinding.FragmentBlankBinding


class BlankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBlankBinding.inflate(layoutInflater)
        return binding.root
    }

}