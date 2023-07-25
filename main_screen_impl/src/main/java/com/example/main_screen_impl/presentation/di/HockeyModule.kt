package com.example.main_screen_impl.presentation.di

import com.example.main_screen_api.interfaces.HockeyRepository
import com.example.main_screen_impl.data.HockeyRepositoryImpl
import com.example.main_screen_impl.di.HockeyAPI
import com.example.main_screen_impl.domain.GetByDateUseCase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class HockeyModule {

    @Provides
    fun provideHockeyRequests (
        retrofit: Retrofit
    ): HockeyAPI = retrofit.create(HockeyAPI ::class.java)

    @Provides
    fun provideRegistrationRepository(
        api : HockeyAPI
    ): HockeyRepository = HockeyRepositoryImpl(api)

    @Provides
    fun provideGetByDateUseCase(
        repository: HockeyRepository
    ): GetByDateUseCase = GetByDateUseCase(repository)
}
