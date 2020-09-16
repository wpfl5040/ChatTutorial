package com.wpfl5.chattutorial.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.LoginCardBackBinding
import com.wpfl5.chattutorial.ui.base.BaseVMFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardRegisterFragment: BaseVMFragment<LoginCardBackBinding, LoginViewModel>() {
    override fun getLayoutRes(): Int = R.layout.login_card_back
    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loginViewModel = viewModel
            btnRegister.setOnClickListener {  }
        }
    }

}