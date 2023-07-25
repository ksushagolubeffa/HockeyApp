package com.example.main_screen_impl.presentation.di

import com.example.main_screen_impl.presentation.fragments.DetailFragment
import com.example.main_screen_impl.presentation.fragments.GameFragment
import com.example.main_screen_impl.presentation.fragments.HomeFragment
import com.example.main_screen_impl.presentation.fragments.InfoFragment
import com.example.main_screen_impl.presentation.fragments.MoneyFragment
import dagger.Subcomponent

@Subcomponent(modules = [HockeyModule::class])
interface HockeyComponent {

    fun injectDetailFragment(fragment: DetailFragment)

    fun injectHomeFragment(fragment: HomeFragment)

    fun injectMoneyFragment(fragment: MoneyFragment)

    fun injectInfoFragment(fragment: InfoFragment)

    fun injectGameFragment(fragment: GameFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): HockeyComponent
    }
}