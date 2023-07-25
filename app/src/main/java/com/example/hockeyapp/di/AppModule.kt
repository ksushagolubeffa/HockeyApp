package com.example.hockeyapp.di

import com.example.hockeyapp.App
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideApp(): App {
        return App()
    }
}