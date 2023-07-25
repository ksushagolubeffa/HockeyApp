package com.example.main_screen_api.interfaces

import com.example.main_screen_api.model.ResponseEntity

interface HockeyRepository {
    suspend fun getGamesByDate(date: String): ResponseEntity
}