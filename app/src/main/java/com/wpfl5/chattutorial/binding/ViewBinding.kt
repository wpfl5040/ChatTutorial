package com.wpfl5.chattutorial.binding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object ViewBinding{

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun visibleGone(view: View, visible: Boolean){
        view.visibility = if(visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("timestampToString")
    fun timestampToString(view: TextView, timestamp: Timestamp?){
        if(timestamp != null){
            val mill = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
            val sdf = SimpleDateFormat("MM/dd HH:mm aaa", Locale.KOREA)
            val netDate = Date(mill)
            val date = sdf.format(netDate).toString()
            view.text = if(date.isEmpty()) "" else date
        }
    }

}