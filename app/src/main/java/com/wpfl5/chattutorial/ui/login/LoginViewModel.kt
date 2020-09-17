package com.wpfl5.chattutorial.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.wpfl5.chattutorial.model.AuthUser
import com.wpfl5.chattutorial.model.FbResponse
import com.wpfl5.chattutorial.model.User
import com.wpfl5.chattutorial.repository.AuthRepository
import com.wpfl5.chattutorial.repository.StoreRepository
import com.wpfl5.chattutorial.ui.EventViewModelDelegate
import com.wpfl5.chattutorial.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

class LoginViewModel
@ViewModelInject constructor(
    private val authRepository: AuthRepository,
    private val storeRepository: StoreRepository,
    val delegate: EventViewModelDelegate
) : BaseViewModel(),
    EventViewModelDelegate by delegate{
    private val _fcmToken: MutableLiveData<String> = MutableLiveData()

    private var _loginLiveData: MutableLiveData<AuthUser> = MutableLiveData()
    @ExperimentalCoroutinesApi
    val loginResponse = _loginLiveData.switchMap { user ->
        liveData(coroutineIoContext) {
            emit(FbResponse.Loading)
            try {
                authRepository.login(user).collect{ emit(it) }
            }catch (e: Exception){
                emit(FbResponse.Fail(e))
            }
        }
    }

    private val _registerAuthLiveData: MutableLiveData<AuthUser> = MutableLiveData()
    val registerAuthResponse = _registerAuthLiveData.switchMap { user ->
        liveData(coroutineIoContext) {
            emit(FbResponse.Loading)
            try {
                authRepository.register(user).collect{ emit(it) }
            }catch (e: Exception){
                emit(FbResponse.Fail(e))
            }
        }
    }

    private val _registerStoreLiveData: MutableLiveData<User> = MutableLiveData()
    val registerStoreResponse = _registerStoreLiveData.switchMap { user ->
        liveData(coroutineIoContext) {
            emit(FbResponse.Loading)
            try {
                storeRepository.setUserData(user).collect{ emit(it) }
            }catch (e: Exception){
                emit(FbResponse.Fail(e))
            }
        }
    }

    fun login(user: AuthUser) {
        _loginLiveData.value = user
    }

    fun registerAuth(user: AuthUser){
        _registerAuthLiveData.value = user
    }

    fun registerStore(id: String, pwd: String, name: String){
        val user = User(id,pwd,name,_fcmToken.value)
        _registerStoreLiveData.value = user
    }


    fun setFcmToken(token: String){
        _fcmToken.value = token
    }

}