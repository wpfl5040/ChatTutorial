package com.wpfl5.chattutorial.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.google.firebase.Timestamp
import com.google.firebase.storage.StorageReference
import com.wpfl5.chattutorial.di.GlideApp
import com.wpfl5.chattutorial.ext.getSpValue
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

    @JvmStatic
    @BindingAdapter("timestampToTime")
    fun timestampToTime(view: TextView, timestamp: Timestamp?){
        if(timestamp != null){
            val mill = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
            val sdf = SimpleDateFormat("HH:mm", Locale.KOREA)
            val netDate = Date(mill)
            val date = sdf.format(netDate).toString()
            view.text = if(date.isEmpty()) "" else date
        }
    }

    @JvmStatic
    @BindingAdapter("roomText")
    fun roomText(view: View, userList: List<String>){
        val myId = view.context.getSpValue("userId", "")
        val friendId = userList.filterNot { it == myId }[0]
        if(view is TextView) view.text = friendId
        else if(view is Toolbar) view.title = friendId
    }

    @JvmStatic
    @BindingAdapter("imageWithUi")
    fun fbImageWithUi(view: ImageView, path: StorageReference?){
        if(path != null){
            GlideApp.with(view.context)
                .load(path)
                .circleCrop()
                .into(view)
        }
    }

}