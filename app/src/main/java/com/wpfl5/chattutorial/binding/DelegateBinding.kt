package com.wpfl5.chattutorial.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.wpfl5.chattutorial.ui.EventViewModelDelegate

object DelegateBinding {

    @JvmStatic
    @BindingAdapter("registerNavigateUp")
    fun registerNavigateUp(view: View, viewModelDelegate: EventViewModelDelegate){
        view.setOnClickListener {
            viewModelDelegate.pressBackButton()
        }
    }
}