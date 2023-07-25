package com.example.hockeyapp

import android.content.Context
import com.example.hockeyapp.di.AppModule
import com.example.hockeyapp.di.NavigationModule
import com.example.hockeyapp.di.SubcomponentsModule
import com.example.main_screen_impl.presentation.di.HockeyComponent
import com.example.main_screen_impl.presentation.di.HockeyModule
import com.example.main_screen_impl.presentation.fragments.DetailFragment
import com.example.main_screen_impl.presentation.fragments.GameFragment
import com.example.main_screen_impl.presentation.fragments.HomeFragment
import com.example.main_screen_impl.presentation.fragments.InfoFragment
import com.example.main_screen_impl.presentation.fragments.MoneyFragment
import com.example.main_screen_impl.presentation.router.HockeyRouter
import com.example.network.NetworkModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        HockeyModule::class,
        NetworkModule::class,
        NavigationModule::class,
        AppModule::class,
        SubcomponentsModule::class
    ]
)
interface AppComponent: MainDependencies {

    fun injectApp(app: App)

    fun injectDetailFragment(fragment: DetailFragment)

    fun injectHomeFragment(fragment: HomeFragment)

    fun injectMoneyFragment(fragment: MoneyFragment)

    fun injectInfoFragment(fragment: InfoFragment)

    fun injectGameFragment(fragment: GameFragment)

    fun injectHockeyRouter(router: HockeyRouter)

    fun inject(activity: MainActivity)

    fun hockeyComponent(): HockeyComponent.Builder

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}