package com.wpfl5.chattutorial.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wpfl5.chattutorial.model.Event
import javax.inject.Inject

interface EventViewModelDelegate {
    val toastObservable: LiveData<Event<Int>>
    val backButtonEvent : LiveData<Event<Unit>>

    fun showToast(textResource: Int)
    fun pressBackButton()
}

class EventViewModelDelegateImp @Inject constructor() : EventViewModelDelegate{
    override val toastObservable = MutableLiveData<Event<Int>>()

    private val _backBtnEvent = MutableLiveData<Event<Unit>>()
    override val backButtonEvent : LiveData<Event<Unit>> get() = _backBtnEvent

    override fun showToast(textResource: Int) {
        toastObservable.value = Event(textResource)
    }

    override fun pressBackButton() {
        _backBtnEvent.value = Event(Unit)
    }

}