package com.wpfl5.chattutorial.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.wpfl5.chattutorial.model.Event
import com.wpfl5.chattutorial.model.EventObserver

abstract class BaseVMActivity<VDB: ViewDataBinding, VM: BaseViewModel>
    : AppCompatActivity() {
    lateinit var binding: VDB

    @LayoutRes
    abstract fun getLayoutRes(): Int
    abstract val viewModel : VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        binding.lifecycleOwner = this@BaseVMActivity

    }


    fun <T> LiveData<T>.observing(owner: LifecycleOwner = this@BaseVMActivity, function: (T) -> Unit) {
        observe(owner, Observer{ function(it) })
    }

    fun <T> LiveData<Event<T>>.eventObserving(owner: LifecycleOwner = this@BaseVMActivity, function: (T) -> Unit) {
        observe(owner, EventObserver { function(it) })
    }

}