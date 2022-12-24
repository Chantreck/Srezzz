package com.chantreck.srez

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.chantreck.srez.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        SharedPrefs.setup(this)
        if (SharedPrefs.getAuthToken().isNotBlank()) {
            startActivity(Intent(this, TasksActivity::class.java))
            return
        }

        lifecycleScope.launch {
            delay(500)
            animateImages()
            animateText()
            animateAlpha()
            delay(1500)
            goNext()
        }
    }

    private fun goNext() {
        startActivity(Intent(this, SignInActivity::class.java))
    }

    private fun animateText() {
        ValueAnimator.ofFloat(1.1f, 0.8f).apply {
            duration = 500
            addUpdateListener {
                val value = it.animatedValue as Float
                binding.textGuideline.setGuidelinePercent(value)
            }
            start()
        }
    }

    private fun animateImages() {
        ValueAnimator.ofFloat(-0.5f, 0.2f).apply {
            duration = 500
            addUpdateListener {
                val value = it.animatedValue as Float
                binding.guideline4.setGuidelinePercent(value)
            }
            start()
        }

        ValueAnimator.ofFloat(1.2f, 0.7f).apply {
            duration = 500
            addUpdateListener {
                val value = it.animatedValue as Float
                binding.guideline5.setGuidelinePercent(value)
            }
            start()
        }
    }

    private fun animateAlpha() {
        ValueAnimator.ofFloat(0.0f, 1.0f).apply {
            duration = 1500
            addUpdateListener {
                val value = it.animatedValue as Float
                binding.image1.alpha = value
                binding.image2.alpha = value
                binding.image3.alpha = value
                binding.image4.alpha = value
            }
            start()
        }
    }
}