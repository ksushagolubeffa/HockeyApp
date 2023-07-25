package com.example.main_screen_impl.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.main_screen_api.model.ResponseEntity
import com.example.main_screen_impl.domain.GetByDateUseCase
import kotlinx.coroutines.launch

class DayViewModel(
    private val useCase: GetByDateUseCase
): ViewModel() {

    private val _games = MutableLiveData<Result<ResponseEntity>>()
    val games: LiveData<Result<ResponseEntity>>
        get() = _games

    private var _error: MutableLiveData<Exception> = MutableLiveData()

    fun getInfoByDate(date:String){
        viewModelScope.launch {
            try {
                Log.e("first view model", date)
                val games = useCase(date)
                _games.value = Result.success(games)
                Log.e("second view model", _games.value.toString())
            } catch (ex: Exception) {
                _games.value = Result.failure(ex)
                _error.value = ex
                Log.e("error", ex.toString())
            }
        }
    }

    companion object{
        fun provideFactory(
            useCase: GetByDateUseCase
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                DayViewModel(
                    useCase
                )
            }
        }
    }
}
