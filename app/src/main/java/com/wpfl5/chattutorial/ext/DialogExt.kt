package com.wpfl5.chattutorial.ext

import android.content.Context

fun Context.dip(value: Int) : Int = (value * resources.displayMetrics.density).toInt()