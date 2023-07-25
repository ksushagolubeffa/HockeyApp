package com.example.main_screen_impl.presentation.rv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.main_screen_api.model.GameEntity
import com.example.main_screen_impl.databinding.ItemGameBinding

class GameHolder(
    private val binding: ItemGameBinding,
    private val onItemClick: (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root){
    fun onBind(game: GameEntity){
        binding.apply {
            // найти и добавить название турнира
            home.visibility = View.INVISIBLE
            guest.visibility = View.INVISIBLE
            tvHome.text = game.linescore.teams.home.team.name
            tvGuest.text = game.linescore.teams.away.team.name
            tvScore.text = "${game.linescore.teams.home.goals}:${game.linescore.teams.away.goals}"
            root.setOnClickListener {
                onItemClick(game.id)
            }
        }
    }
}