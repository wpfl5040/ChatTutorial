package com.wpfl5.chattutorial.ext

import android.view.View

fun View.visible(b: Boolean) {
    visibility = if(b) View.VISIBLE else View.INVISIBLE
}
fun View.gone() {
    visibility = View.GONE
}

fun View.visible(){
    visibility = View.VISIBLE
}