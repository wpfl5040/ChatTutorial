package com.wpfl5.chattutorial.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.wpfl5.chattutorial.model.Event
import com.wpfl5.chattutorial.model.EventObserver

abstract class BaseVMFragment<VDB: ViewDataBinding, VM: BaseViewModel> : Fragment() {
    lateinit var binding: VDB

    @LayoutRes
    abstract fun getLayoutRes(): Int
    abstract val viewModel : VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    fun <T> LiveData<T>.observing(owner: LifecycleOwner = this@BaseVMFragment, function: (T) -> Unit) {
        observe(owner, Observer{ function(it) })
    }

    fun <T> LiveData<Event<T>>.eventObserving(function: (T) -> Unit) {
        observe(this@BaseVMFragment, EventObserver { function(it) })
    }
}