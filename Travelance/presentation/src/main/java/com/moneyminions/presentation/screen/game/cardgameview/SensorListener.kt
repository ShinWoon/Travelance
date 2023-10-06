package com.moneyminions.presentation.screen.game.cardgameview // ktlint-disable filename
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.moneyminions.presentation.utils.SensorUtil

private const val TAG = "SensorListener"
class ShakeDetector(private val context: Context) {
    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private val accelerationThreshold = 2.0f
    private var listener: (() -> Unit)? = null

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                val axisX = event.values[0]
                val axisY = event.values[1]
                val axisZ = event.values[2]

                if (SensorUtil.isShake(axisX, axisY, axisZ)) {
                    Log.d(TAG, "onSensorChanged: 감지")
                    listener?.invoke()
                }
            }
        }
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }
    }

    fun startListening(listener: () -> Unit) {
        this.listener = listener
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun stopListening() {
        this.listener = null
        sensorManager.unregisterListener(sensorEventListener)
    }
}
