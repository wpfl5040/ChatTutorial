package com.wpfl5.chattutorial.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.wpfl5.chattutorial.model.FbResponse
import com.wpfl5.chattutorial.model.User
import com.wpfl5.chattutorial.repository.AuthRepository
import com.wpfl5.chattutorial.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

class LoginViewModel
@ExperimentalCoroutinesApi
@ViewModelInject
constructor(
    private val repository: AuthRepository
) : BaseViewModel() {

    private var _userLiveData: MutableLiveData<User> = MutableLiveData()
    @ExperimentalCoroutinesApi
    val loginResponse = _userLiveData.switchMap { user ->
        liveData(coroutineIoContext) {
            emit(FbResponse.Loading)
            try {
                repository.login(user).collect{ emit(it) }
            }catch (e: Exception){
                emit(FbResponse.Fail(e))
            }
        }
    }

    fun login(user: User) {
        _userLiveData.value = user
    }

}