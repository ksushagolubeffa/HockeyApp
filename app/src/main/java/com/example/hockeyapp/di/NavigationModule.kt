package com.example.hockeyapp.di

import com.example.hockeyapp.ApplicationScope
import com.example.main_screen_impl.presentation.router.HockeyRouter
import dagger.Module
import dagger.Provides

@Module
class NavigationModule {

    @ApplicationScope
    @Provides
    fun provideNavigator(): Navigator = Navigator()

    @ApplicationScope
    @Provides
    fun provideHockeyRouter(navigator: Navigator): HockeyRouter = navigator
}