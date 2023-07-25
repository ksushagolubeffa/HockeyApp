package com.example.hockeyapp.di

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import com.example.hockeyapp.R
import com.example.main_screen_impl.presentation.router.HockeyRouter

class Navigator: HockeyRouter {

    private var navController: NavController? = null

    fun initialize(navController: NavController) {
        this.navController = navController
    }

    fun attachNavController(navController: NavController, graph: Int) {
        navController.setGraph(graph)
        this.navController = navController
    }

    override fun openMoneyFragment(bundle: Bundle) {

    }

    override fun openHomeFragment(bundle: Bundle?) {
        navController?.navigate(R.id.action_moneyFragment_to_homeFragment, bundle)
    }

    override fun openDetailFragment(bundle: Bundle) {
        navController?.navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

    override fun openInfoFragment() {
        navController?.navigate(R.id.action_homeFragment_to_infoFragment)
    }

    override fun openCurrentGamesFragment() {
        TODO("Not yet implemented")
    }
}