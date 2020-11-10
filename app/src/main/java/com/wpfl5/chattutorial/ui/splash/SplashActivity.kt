package com.wpfl5.chattutorial.ui.splash

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.ActivitySplashBinding
import com.wpfl5.chattutorial.ext.getSpValue
import com.wpfl5.chattutorial.ext.startAct
import com.wpfl5.chattutorial.ui.base.BaseActivity
import com.wpfl5.chattutorial.ui.login.LoginActivity
import com.wpfl5.chattutorial.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun getLayoutRes(): Int = R.layout.activity_splash

    @Inject lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferenceId = getSpValue("userId","")
        MainScope().launch {
            delay(1000L)
            finish()

            if(preferenceId.isBlank() || auth.currentUser == null){
                startAct<LoginActivity>()
            }else{
                if(auth.currentUser!!.email == preferenceId){
                    startAct<MainActivity>()
                }else{
                    startAct<LoginActivity>()
                }
            }

        }

    }
}