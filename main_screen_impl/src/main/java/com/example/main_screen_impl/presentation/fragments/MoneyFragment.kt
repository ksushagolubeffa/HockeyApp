package com.example.main_screen_impl.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.main_screen_impl.R
import com.example.main_screen_impl.databinding.FragmentCoinsBinding
import com.example.main_screen_impl.databinding.FragmentHomePageBinding
import com.example.main_screen_impl.presentation.ShakeDetector
import com.example.main_screen_impl.presentation.di.HockeyComponentProvider
import com.example.main_screen_impl.presentation.router.HockeyRouter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoneyFragment: Fragment(R.layout.fragment_coins), ShakeDetector.OnShakeListener {

    private var money = 0
    private var binding: FragmentCoinsBinding? = null

    private lateinit var shakeDetector: ShakeDetector
    private lateinit var countDownTimer: CountDownTimer

    private val SHAKING_DURATION: Long = 5000

    @Inject
    lateinit var router: HockeyRouter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val hockeyComponent = (requireActivity().application as HockeyComponentProvider)
            .provideHockeyComponent()
        hockeyComponent.injectMoneyFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        binding = FragmentCoinsBinding.bind(view)
        money = arguments?.getInt("MONEY") ?: 0
        shakeDetector = ShakeDetector(this)
        binding?.chest?.setOnClickListener {
            startShaking()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun startShaking() {
        countDownTimer = object : CountDownTimer(SHAKING_DURATION, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                binding?.apply {
                    tvMoney.text = "Поздравляем! Вы получили 3 монеты"
                    coins.visibility = View.VISIBLE
                    chest.visibility = View.INVISIBLE
                }
                val bundle: Bundle = Bundle().apply{
                    putInt("MONEY", money + 3)
                }
                lifecycleScope.launch{
                     delay(1000)
                    router.openHomeFragment(bundle)
                }
            }
        }

        countDownTimer.start()
    }

    override fun onShake() {
        startShaking()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        countDownTimer.cancel()
    }
}
