package com.example.main_screen_impl.presentation

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import java.lang.Math.sqrt

class ShakeDetector(private val onShakeListener: OnShakeListener) : SensorEventListener {
    private var shakeTimestamp: Long = 0
    private var shakeCount = 0

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0].toDouble()
        val y = event.values[1].toDouble()
        val z = event.values[2].toDouble()

        val acceleration = kotlin.math.sqrt(x * x + y * y + z * z)
        val now = System.currentTimeMillis()

        if (acceleration > SHAKE_THRESHOLD) {
            if (shakeTimestamp + SHAKE_INTERVAL > now) {
                return
            }

            shakeTimestamp = now
            shakeCount++

            if (shakeCount >= SHAKE_COUNT) {
                onShakeListener.onShake()
                shakeCount = 0
            }
        }
    }

    interface OnShakeListener {
        fun onShake()
    }

    companion object {
        private const val SHAKE_THRESHOLD = 2.7
        private const val SHAKE_INTERVAL = 1000
        private const val SHAKE_COUNT = 10
    }
}