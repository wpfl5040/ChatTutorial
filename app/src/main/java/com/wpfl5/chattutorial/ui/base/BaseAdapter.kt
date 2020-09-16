package com.wpfl5.chattutorial.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter


abstract class BaseAdapter<Model, Binding : ViewDataBinding>(diffUtil: DiffUtil.ItemCallback<Model>) :
    ListAdapter<Model, BaseViewHolder<Binding>>(diffUtil) {

    abstract val layoutRes: Int

    abstract fun onBindViewHolder(binding: Binding, item: Model)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder<Binding>(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutRes,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: BaseViewHolder<Binding>, position: Int) {
        onBindViewHolder(holder.binding, getItem(position))
    }

}