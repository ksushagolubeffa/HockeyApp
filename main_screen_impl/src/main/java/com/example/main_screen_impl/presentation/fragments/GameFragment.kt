package com.example.main_screen_impl.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.main_screen_api.model.GameEntity
import com.example.main_screen_api.model.ResponseEntity
import com.example.main_screen_impl.R
import com.example.main_screen_impl.databinding.FragmentGameBinding
import com.example.main_screen_impl.domain.GetByDateUseCase
import com.example.main_screen_impl.presentation.di.HockeyComponentProvider
import com.example.main_screen_impl.presentation.router.HockeyRouter
import com.example.main_screen_impl.presentation.viewmodel.DayViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class GameFragment : Fragment(R.layout.fragment_game) {

    private var binding: FragmentGameBinding? = null

    private var query: String = ""

    private var date: LocalDate = LocalDate.now()

    private var queryDetector: String = ""

    private var isFound: Boolean = false

    private var searchView: SearchView? = null

    @Inject
    lateinit var router: HockeyRouter

    @Inject
    lateinit var getByDateUseCase: GetByDateUseCase

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
        initObservers()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val hockeyComponent = (requireActivity().application as HockeyComponentProvider)
            .provideHockeyComponent()
        hockeyComponent.injectGameFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentGameBinding.bind(view)
        searchView = binding?.searchView
        setOnSearchViewClickListener()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setOnSearchViewClickListener() {
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(quer: String): Boolean {
                lifecycleScope.launch {
                    try {
                        query = quer
                        viewModel.getInfoByDate("2023-02-26") //date.toString()
                        searchView?.clearFocus()
                    } catch (exception: Exception) {
                        withContext(Dispatchers.Main) {
                            binding?.tvDefault?.visibility = View.VISIBLE
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }


        })

    }

    private fun loadInfo(response: ResponseEntity) {
        lifecycleScope.launch {
            queryDetector = query
            val games = response.dates[0].games
            var game: GameEntity? = null
            games.forEach {
                if (it.linescore.teams.away.team.name == query || it.linescore.teams.home.team.name == query) {
                    game = it
                }
            }
            if (game != null) {
                Log.e("Game load away", game!!.linescore.teams.away.team.name)
                Log.e("Game load home", game!!.linescore.teams.home.team.name)
                isFound = true
                val teams = game!!.linescore.teams
                Log.e("teams game", teams.toString())
                Log.e("binding check", "${binding == null}")
                binding?.apply {
                    tvDefault.visibility = View.INVISIBLE
                    tvGame.visibility = View.VISIBLE //
                    tvGuest.visibility = View.VISIBLE
                    tvHome.visibility = View.VISIBLE
                    tvScore.visibility = View.VISIBLE
                    tvTime.visibility = View.VISIBLE //
                    tvAl1.visibility = View.VISIBLE
                    tvAl2.visibility = View.VISIBLE
                    tvGoalAway.visibility = View.VISIBLE
                    tvGoalHome.visibility = View.VISIBLE
                    tvShotsAway.visibility = View.VISIBLE
                    tvShotsHome.visibility = View.VISIBLE
                    periods.visibility = View.VISIBLE

                    tvHome.text = teams.home.team.name
                    Log.e("tvHome", tvHome.text.toString())
                    tvGuest.text = teams.away.team.name
                    tvGoalHome.text = teams.home.goals.toString()
                    tvGoalAway.text = teams.away.goals.toString()
                    tvShotsHome.text = teams.home.shotsOnGoal.toString()
                    tvShotsAway.text = teams.away.shotsOnGoal.toString()
                    tvScore.text = "${teams.home.goals}:${teams.away.goals}"
                }
            } else {
                isFound = false
                binding?.apply {
                    tvDefault.visibility = View.VISIBLE
//                home.visibility = View.INVISIBLE
//                guest.visibility = View.INVISIBLE
                    tvGame.visibility = View.INVISIBLE
                    tvGuest.visibility = View.INVISIBLE
                    tvHome.visibility = View.INVISIBLE
                    tvScore.visibility = View.INVISIBLE
                    tvTime.visibility = View.INVISIBLE
                    tvAl1.visibility = View.INVISIBLE
                    tvAl2.visibility = View.INVISIBLE
                    tvGoalAway.visibility = View.INVISIBLE
                    tvGoalHome.visibility = View.INVISIBLE
                    tvShotsAway.visibility = View.INVISIBLE
                    tvShotsHome.visibility = View.INVISIBLE
                    periods.visibility = View.INVISIBLE
                }
            }

        }
        while (query == queryDetector) {
            lifecycleScope.launch{
                delay(60000)
                viewModel.getInfoByDate("2023-02-26") //date.toString()
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
