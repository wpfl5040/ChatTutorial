package com.wpfl5.chattutorial.ui.base

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.res.ResourcesCompat
import com.wpfl5.chattutorial.R
import com.wpfl5.chattutorial.ext.dip

class AppDialog(context: Context?) : AppCompatDialog(context, R.style.AppTheme_BaseDialog) {

    init {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.run {
//            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    override fun setContentView(view: View) {
        super.setContentView(wrap(view))
    }

    private fun wrap(content: View): View {
        val verticalMargin = context.dip(32)
        val horizontalMargin = context.dip(32)
        return FrameLayout(context).apply {
            addView(content, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            ).apply {
                //setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin)
                gravity = Gravity.CENTER
            })
            val rect = Rect()
            setOnTouchListener { view, motionEvent ->
                when (motionEvent.action) {
                    // The FrameLayout is technically inside the dialog, but we treat it as outside.
                    MotionEvent.ACTION_DOWN -> {
                        content.getGlobalVisibleRect(rect)
                        if (!rect.contains(motionEvent.x.toInt(), motionEvent.y.toInt())) {
                            cancel()
                            false
                        } else {
                            false
                        }
                    }
                    MotionEvent.ACTION_UP   -> {
                        view.performClick()
                    }
                    else                    -> {
                        false
                    }
                }
            }

            background = ColorDrawable(ResourcesCompat.getColor(resources, R.color.scrim, context.theme))
        }
    }
}