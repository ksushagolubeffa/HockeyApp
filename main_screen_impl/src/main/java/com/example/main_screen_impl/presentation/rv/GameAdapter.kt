package com.example.main_screen_impl.presentation.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.main_screen_api.model.GameEntity
import com.example.main_screen_impl.databinding.ItemGameBinding

class GameAdapter(
    private var games: List<GameEntity>?,
    private val onItemClick:(Long) -> (Unit)
): RecyclerView.Adapter<GameHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder = GameHolder(
        binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onItemClick = onItemClick
    )

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        if(games != null) {
            holder.onBind(games!![position])
        }
    }

    override fun getItemCount(): Int = games?.size ?: 0
}