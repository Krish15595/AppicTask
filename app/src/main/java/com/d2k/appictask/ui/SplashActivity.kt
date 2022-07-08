package com.d2k.appictask.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.d2k.appictask.R
import com.d2k.appictask.databinding.ActivitySplashBinding
import com.d2k.appictask.ui.carousel.CarouselActivity
import com.d2k.appictask.ui.login.LoginActivity
import com.d2k.appictask.util.Constants.Companion.IS_FIRST_LAUNCH
import com.d2k.tmb.base.BaseActivity
import com.d2k.tmb.extension.launchActivity


class SplashActivity : BaseActivity() {
    lateinit var activitySplashBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySplashBinding =
            DataBindingUtil.setContentView(this@SplashActivity, R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            activitySplashBinding.bankLoad.visibility = View.GONE
            activitySplashBinding.llMain.visibility = View.VISIBLE
            val aniSlide: Animation =
                AnimationUtils.loadAnimation(applicationContext, R.anim.silde_up)
            activitySplashBinding.ivLogo.startAnimation(aniSlide)
            Handler(Looper.getMainLooper()).postDelayed({
                if (prefUtils.getBoolean(IS_FIRST_LAUNCH))
                    launchActivity<LoginActivity> { finish() }
                else
                    launchActivity<CarouselActivity> { finish() }
            }, 1000)
        }, 1000)


    }
}