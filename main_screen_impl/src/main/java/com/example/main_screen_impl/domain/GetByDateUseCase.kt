package com.example.main_screen_impl.domain

import android.util.Log
import com.example.main_screen_api.interfaces.HockeyRepository
import com.example.main_screen_api.model.ResponseEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetByDateUseCase(
    private val repository: HockeyRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    suspend operator fun invoke(date:String): ResponseEntity{
        return withContext(dispatcher){
            Log.e("UseCase", date)
            repository.getGamesByDate("2023-02-26")
        }
    }
}
