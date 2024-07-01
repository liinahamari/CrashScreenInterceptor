package dev.liinahamari.crashscreeninterceptor

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.liinahamari.crash_screen.CrashInterceptor
import dev.liinahamari.crash_screen.api.CrashScreenDependencies
import dev.liinahamari.crash_screen_interceptor.sample.R

class SampleActivity : AppCompatActivity(R.layout.activity_sample) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        CrashInterceptor.init(object : CrashScreenDependencies {
            override val context: Context
                get() = this@SampleActivity.applicationContext
            override val doWhileImpossibleToStartCrashScreen: (Throwable) -> Unit
                get() = { Log.d("TAG", "failed to show interceptor screen") }
            override val doOnCrash: (Throwable) -> Unit
                get() = { Log.d("TAG", "Crash logged with cause (${it.cause})") }
        })

        findViewById<Button>(R.id.throw_exception_button).setOnClickListener {
            throw IllegalStateException()
        }
    }
}
