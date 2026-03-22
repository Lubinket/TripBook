package com.tripbook.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.tripbook.app.databinding.ActivitySplashBinding

/**
 * SplashActivity is the first screen the user sees.
 * It shows the TripBook logo for 2 seconds then navigates to MainActivity.
 *
 * android:noHistory="true" in the manifest ensures pressing Back
 * from MainActivity cannot return here.
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigate to MainActivity after 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            // Slide transition: new activity comes in from right
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, SPLASH_DELAY)
    }

    companion object {
        private const val SPLASH_DELAY = 2000L // 2 seconds
    }
}