package com.wpfl5.chattutorial.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.LoginCardFrontBinding
import com.wpfl5.chattutorial.ext.*
import com.wpfl5.chattutorial.model.request.AuthUser
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.ui.base.BaseVMFragment
import com.wpfl5.chattutorial.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardLoginFragment: BaseVMFragment<LoginCardFrontBinding, LoginViewModel>() {

    override fun getLayoutRes(): Int = R.layout.login_card_front
    override val viewModel: LoginViewModel by activityViewModels()
    private lateinit var userId: String

    override fun onStart() {
        super.onStart()
        loginObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loginViewModel = viewModel
            signUp.setOnClickListener { flipCard() }
            btnLogin.setOnClickListener { login() }
        }
    }

    private fun flipCard() {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.animator.card_flip_right_in,
                R.animator.card_flip_right_out,
                R.animator.card_flip_left_in,
                R.animator.card_flip_left_out
            )
            .replace(R.id.card_container, CardRegisterFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun login(){
        with(binding){
            initError(inputId, inputPassword)

            val id = inputId.textString
            val pwd = inputPassword.textString

            when{
                id.isNullOrBlank() -> inputId.errorText(R.string.error_id)
                pwd.isNullOrBlank() -> inputPassword.errorText(R.string.error_pwd)
                else -> {
                    userId = id
                    viewModel.login(AuthUser(id, pwd))
                }
            }
        }
    }


    private fun loginObserver(){
        viewModel.loginResponse.observing(viewLifecycleOwner) { result ->
            when(result){
                is FbResponse.Loading -> {
                    loading(true)
                }
                is FbResponse.Success -> {
                    requireContext().putSpValue("userId", userId)
                    requireActivity().finish()
                    startAct<MainActivity>()
                }
                is FbResponse.Fail -> {
                    loading(false)
                    toast(requireContext(), result.e.message)
                }
            }
        }
    }

    private fun loading(visibility: Boolean){
        with(binding){
            if(visibility){
                progressLogin.visible()
                btnLogin.gone()
            }else{
                progressLogin.gone()
                btnLogin.visible()
            }
        }

    }

}