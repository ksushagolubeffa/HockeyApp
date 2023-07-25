package com.example.main_screen_impl.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.main_screen_api.model.GameEntity
import com.example.main_screen_api.model.ResponseEntity
import com.example.main_screen_impl.R
import com.example.main_screen_impl.databinding.FragmentDetailBinding
import com.example.main_screen_impl.domain.GetByDateUseCase
import com.example.main_screen_impl.presentation.di.HockeyComponentProvider
import com.example.main_screen_impl.presentation.router.HockeyRouter
import com.example.main_screen_impl.presentation.viewmodel.DayViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var binding: FragmentDetailBinding? = null

    @Inject
    lateinit var getByDateUseCase: GetByDateUseCase

    @Inject
    lateinit var router: HockeyRouter

    private var gameId: Long = 0

    private val viewModel: DayViewModel by viewModels {
        DayViewModel.provideFactory(
            getByDateUseCase
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        initObservers()
        gameId = arguments?.getLong("GAME_ID") ?: 0L
        val date = arguments?.getString("DATE")
        if (date != null) {
            initGame(date)
        }
        Log.e("date", date.toString())
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val hockeyComponent = (requireActivity().application as HockeyComponentProvider)
            .provideHockeyComponent()
        hockeyComponent.injectDetailFragment(this)
    }

    private fun initGame(date: String) {
        lifecycleScope.launch {
            viewModel.getInfoByDate(date)
        }
    }

    private fun loadInfo(response: ResponseEntity) {
        Log.e("response detail", response.toString())
        lifecycleScope.launch {
            val games = response.dates[0].games
            var game: GameEntity? = null
            games.forEach {
                if (it.id == gameId) {
                    game = it
                }
            }
            if (game != null) {
                val periods = game!!.linescore.periods
                val teams = game!!.linescore.teams
                binding?.apply {
                    tvHome.text = teams.home.team.name
                    Log.e("tvHome", teams.home.team.name)
                    Log.e("tv text", tvHome.text.toString())
                    tvGuest.text = teams.away.team.name
                    tvGoalHome.text = teams.home.goals.toString()
                    tvGoalAway.text = teams.away.goals.toString()
                    tvShotsHome.text = teams.home.shotsOnGoal.toString()
                    tvShotsAway.text = teams.away.shotsOnGoal.toString()
                    tvScore.text = "${teams.home.goals}:${teams.away.goals}"
                    tvGoals1Away.text = periods[0].away.goals.toString()
                    tvShots1Away.text = periods[0].away.shotsOnGoal.toString()
                    tvRink1Away.text = periods[0].away.rinkSide
                    tvGoals1Home.text = periods[0].home.goals.toString()
                    tvShots1Home.text = periods[0].home.shotsOnGoal.toString()
                    tvRink1Home.text = periods[0].home.rinkSide
                    tvGoals2Away.text = periods[1].away.goals.toString()
                    tvShots2Away.text = periods[1].away.shotsOnGoal.toString()
                    tvRink2Away.text = periods[1].away.rinkSide
                    tvGoals2Home.text = periods[1].home.goals.toString()
                    tvShots2Home.text = periods[1].home.shotsOnGoal.toString()
                    tvRink2Home.text = periods[1].home.rinkSide
                    tvGoals3Away.text = periods[2].away.goals.toString()
                    tvShots3Away.text = periods[2].away.shotsOnGoal.toString()
                    tvRink3Away.text = periods[2].away.rinkSide
                    tvGoals3Home.text = periods[2].home.goals.toString()
                    tvShots3Home.text = periods[2].home.shotsOnGoal.toString()
                    tvRink3Home.text = periods[2].home.rinkSide
                }

            }
        }
    }

    private fun initObservers() {
        viewModel.games.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = {
                    loadInfo(it)
                },
                onFailure = {
                    Log.e("DetailFragment", "shit happend")
                }
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
