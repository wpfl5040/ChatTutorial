package com.wpfl5.chattutorial.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.databinding.LoginCardBackBinding
import com.wpfl5.chattutorial.ext.*
import com.wpfl5.chattutorial.model.request.AuthUser
import com.wpfl5.chattutorial.model.response.FbResponse
import com.wpfl5.chattutorial.ui.base.BaseVMFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardRegisterFragment : BaseVMFragment<LoginCardBackBinding, LoginViewModel>() {
    override fun getLayoutRes(): Int = R.layout.login_card_back
    override val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loginViewModel = viewModel
            btnRegister.setOnClickListener { register() }
        }
    }


    private fun register(){
        with(binding){
            initError(inputId, inputPassword, inputName)

            val id = inputId.textString
            val pwd = inputPassword.textString
            val name = inputName.textString

            when{
                id.isNullOrBlank() -> inputId.errorText(R.string.error_id)
                pwd.isNullOrBlank() -> inputPassword.errorText(R.string.error_pwd)
                name.isNullOrBlank() -> inputName.errorText(R.string.error_name)
                else -> { registerAuthObserver(id,pwd,name) }
            }
        }
    }


    private fun registerAuthObserver(id: String, pwd: String, name: String){
        viewModel.registerAuth(
            AuthUser(
                id,
                pwd
            )
        )
        viewModel.registerAuthResponse.observing(viewLifecycleOwner) { result ->
            when(result){
                is FbResponse.Loading -> { loading(true) }
                is FbResponse.Success -> { registerStoreObserver(id, name) }
                is FbResponse.Fail -> {
                    loading(false)
                    toast(requireContext(), result.e.message)
                }
            }
        }
    }

    private fun registerStoreObserver(id: String, name: String){
        viewModel.registerStore(id,name)
        viewModel.registerStoreResponse.observing(viewLifecycleOwner) { result->
            when(result){
                is FbResponse.Loading -> { }
                is FbResponse.Success -> {
                    loading(false)
                    viewModel.pressBackButton()
                    toast(requireContext(), "success")
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
                registerProgress.visible()
                btnRegister.gone()
            }else{
                registerProgress.gone()
                btnRegister.visible()
            }
        }

    }



}