package com.example.hockeyapp

import android.app.Application
import com.example.main_screen_impl.presentation.di.HockeyComponent
import com.example.main_screen_impl.presentation.di.HockeyComponentProvider

open class App: Application(), HockeyComponentProvider {

    override fun onCreate() {

        appComponent = DaggerAppComponent
            .builder()
            .context(applicationContext)
            .build()

        super.onCreate()

    }
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun provideHockeyComponent(): HockeyComponent {
        return appComponent.hockeyComponent().build()
    }
}
