package com.wpfl5.chattutorial.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.wpfl5.chattutorial.model.Event
import com.wpfl5.chattutorial.model.EventObserver


abstract class BaseVMDialog<VDB : ViewDataBinding, VM : ViewModel>
    : AppCompatDialogFragment() {

    abstract val viewModel: VM
    abstract val layoutResId: Int

    lateinit var binding: VDB

    fun viewModel(init: VM.() -> Unit) = viewModel.apply(init)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AppDialog(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.onViewBound(view, savedInstanceState)
    }

    abstract fun VDB.onViewBound(view: View, savedInstanceState: Bundle?)

    fun <T> LiveData<T>.observing(owner: LifecycleOwner= this@BaseVMDialog, function: (T) -> Unit) {
        observe(owner, Observer{ function(it) })
    }

    fun <T> LiveData<Event<T>>.eventObserving(owner: LifecycleOwner= this@BaseVMDialog, function: (T) -> Unit) {
        observe(owner, EventObserver { function(it) })
    }
}