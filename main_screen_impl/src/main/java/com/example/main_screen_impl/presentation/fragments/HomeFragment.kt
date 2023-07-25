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
import androidx.recyclerview.widget.RecyclerView
import com.example.main_screen_impl.R
import com.example.main_screen_impl.databinding.FragmentDetailBinding
import com.example.main_screen_impl.databinding.FragmentHomePageBinding
import com.example.main_screen_impl.domain.GetByDateUseCase
import com.example.main_screen_impl.presentation.di.HockeyComponentProvider
import com.example.main_screen_impl.presentation.router.HockeyRouter
import com.example.main_screen_impl.presentation.rv.GameAdapter
import com.example.main_screen_impl.presentation.viewmodel.DayViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

//добавить навигацию на экран монет
class HomeFragment: Fragment(R.layout.fragment_home_page) {

    private var recyclerView: RecyclerView? = null

    private var money: Int = 3

    private var binding: FragmentHomePageBinding? = null

    private var localDate: LocalDate = LocalDate.now()

    private val startDate: LocalDate = LocalDate.now()

    private var date: String = "${localDate.dayOfMonth} ${localDate.month.name}, ${localDate.year}"

    @Inject
    lateinit var getByDateUseCase: GetByDateUseCase

    @Inject
    lateinit var router: HockeyRouter

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
        money += arguments?.getInt("MONEY") ?: 0
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val hockeyComponent = (requireActivity().application as HockeyComponentProvider)
            .provideHockeyComponent()
        hockeyComponent.injectHomeFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomePageBinding.bind(view)
        recyclerView = binding?.rv
        rvCreator(localDate.toString())
        binding?.apply{
            tvMoney.text = money.toString()
            info.setOnClickListener {
                router.openInfoFragment()
            }
            tvDate.text = date
            next.setOnClickListener {
                localDate = localDate.plusDays(1)
                date = "${localDate.dayOfMonth} ${localDate.month.name}, ${localDate.year}"
                tvDate.text = date
                rvCreator(localDate.toString())
                if(localDate!=startDate){
                    Log.e("local date", localDate.toString())
                    Log.e("startDate", startDate.toString())
                    next.visibility = View.VISIBLE
                }else{
                    next.visibility = View.INVISIBLE
                }
            }
            back.setOnClickListener{
                localDate = localDate.minusDays(1)
                next.visibility = View.VISIBLE
                date = "${localDate.dayOfMonth} ${localDate.month.name}, ${localDate.year}"
                tvDate.text = date
                rvCreator(localDate.toString())
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun rvCreator(date:String){
        Log.e("Fragment rv", date)
        viewModel.getInfoByDate(date)
    }

    private fun onGameItemClick(id: Long){
        if(money>4) {
            val bundle: Bundle = Bundle().apply {
                putLong("GAME_ID", id)
                putString(
                    "DATE",
                    "2023-02-26"
//                    localDate.toString()
                )
            }
            money -= 5
            router.openDetailFragment(bundle)
        } else{
            Snackbar.make(requireView(), "У вас недостаточно монет. Скорее заработайте монеты и смотрите информацию об игре", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun initObservers() {
        viewModel.games.observe(viewLifecycleOwner) {
            it.fold(
                onSuccess = { listModel ->
                    recyclerView?.run {
                        lifecycleScope.launch {
                            adapter = if(listModel.dates.isEmpty()) {
                                GameAdapter(
                                    null,
                                    onItemClick = ::onGameItemClick
                                )
                            }else{
                                GameAdapter(
                                    listModel.dates[0].games,
                                    onItemClick = ::onGameItemClick
                                )
                            }
                        }
                    }
                },
                onFailure = {
                    Log.e("HomeFragment", "shit happend")
                }
            )
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("MONEY", money)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let{
            money = it.getInt("MONEY")
            binding?.tvMoney?.text = "$money"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}