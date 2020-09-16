package com.wpfl5.chattutorial.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.ActivityLoginBinding
import com.wpfl5.chattutorial.ui.base.BaseVMActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : BaseVMActivity<ActivityLoginBinding, LoginViewModel>() {
    override fun getLayoutRes(): Int = R.layout.activity_login
    override val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.card_container, CardLoginFragment())
                .commit()
        }
        binding.apply {
            loginViewModel = viewModel
        }
    }


}