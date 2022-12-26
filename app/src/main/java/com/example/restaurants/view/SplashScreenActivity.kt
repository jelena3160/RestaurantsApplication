package com.example.restaurants.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.example.restaurants.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.delay

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        startAnimation()

    }

    private fun startAnimation() {
        makeIconsInvisible()
        iIndian.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))

        Handler().postDelayed({
            iThai.alpha = 1f
            iThai.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))
        }, 600)

        Handler().postDelayed({
            iDessert.alpha = 1f
            iDessert.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))
        }, 1200)

        Handler().postDelayed({
            iPizza.alpha = 1f
            iPizza.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))
        }, 1800)


        Handler().postDelayed({
            iSushi.alpha = 1f
            iSushi.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))
        }, 2400)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3400)

    }

    private fun makeIconsInvisible(){
        iSushi.alpha = 0f
        iPizza.alpha = 0f
        iDessert.alpha = 0f
        iThai.alpha = 0f
    }
}