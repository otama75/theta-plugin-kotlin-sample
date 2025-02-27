package io.github.otama75.thetaKotlin

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import com.theta360.pluginlibrary.activity.PluginActivity
import com.theta360.pluginlibrary.callback.KeyCallback
import com.theta360.pluginlibrary.receiver.KeyReceiver
import com.theta360.pluginlibrary.values.LedColor
import com.theta360.pluginlibrary.values.LedTarget
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : PluginActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
    var count = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Theta Plugin Initialization
        notificationCameraClose()

        // Start Camera LED blinking
        notificationLedBlink(LedTarget.LED7, LedColor.BLUE, 300)

        // Setting Key Callback
        class KotlinKeyCallback : KeyCallback {
            override fun onKeyDown(keyCode: Int, event: KeyEvent?) {
                if (keyCode == KeyReceiver.KEYCODE_CAMERA) {
                    if (count % 2 == 0) {
                        notificationLedBlink(LedTarget.LED7, LedColor.BLUE, 300)
                    } else {
                        notificationLedHide(LedTarget.LED7)
                    }
                    val message = "Camera button pushed ${count++} times"
                    tvCounter.text = message
                    Log.d(TAG, message)
                }
            }
            override fun onKeyUp(p0: Int, p1: KeyEvent?) {}
            override fun onKeyLongPress(p0: Int, p1: KeyEvent?) {}
        }
        setKeyCallback(KotlinKeyCallback())
    }
}
