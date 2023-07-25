package com.example.main_screen_impl.presentation.router

import android.os.Bundle

interface HockeyRouter {

    fun openMoneyFragment(bundle: Bundle)

    fun openHomeFragment(bundle: Bundle?)

    fun openDetailFragment(bundle:Bundle)

    fun openInfoFragment()

    fun openCurrentGamesFragment()
}