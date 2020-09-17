package com.wpfl5.chattutorial.ui.splash

import android.os.Bundle
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.ActivitySplashBinding
import com.wpfl5.chattutorial.ext.startAct
import com.wpfl5.chattutorial.ui.base.BaseActivity
import com.wpfl5.chattutorial.ui.main.MainActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun getLayoutRes(): Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainScope().launch {
            delay(1000L)
            finish()
            startAct<MainActivity>()
        }

    }
}