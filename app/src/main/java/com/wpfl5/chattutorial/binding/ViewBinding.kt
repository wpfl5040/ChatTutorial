package com.wpfl5.chattutorial.binding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.firebase.Timestamp

object ViewBinding{

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun visibleGone(view: View, visible: Boolean){
        view.visibility = if(visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("timestampToString")
    fun timestampToString(view: TextView, timestamp: Timestamp){

    }

}