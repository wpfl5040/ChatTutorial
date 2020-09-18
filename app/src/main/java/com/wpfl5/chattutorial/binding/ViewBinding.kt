package com.wpfl5.chattutorial.binding

import android.view.View
import androidx.databinding.BindingAdapter

object ViewBinding{

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun visibleGone(view: View, visible: Boolean){
        view.visibility = if(visible) View.VISIBLE else View.GONE
    }


}