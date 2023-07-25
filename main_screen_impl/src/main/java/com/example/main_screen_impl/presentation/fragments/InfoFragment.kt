package com.example.main_screen_impl.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.main_screen_impl.R
import com.example.main_screen_impl.databinding.FragmentInfoBinding
import com.example.main_screen_impl.presentation.di.HockeyComponentProvider

class InfoFragment : Fragment(R.layout.fragment_info) {

    private var binding: FragmentInfoBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val hockeyComponent = (requireActivity().application as HockeyComponentProvider)
            .provideHockeyComponent()
        hockeyComponent.injectInfoFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentInfoBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}