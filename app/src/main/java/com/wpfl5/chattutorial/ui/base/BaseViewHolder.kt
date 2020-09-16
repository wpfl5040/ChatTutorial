package com.wpfl5.chattutorial.ui.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<Binding : ViewDataBinding>(val binding: Binding) : RecyclerView.ViewHolder(binding.root)
