package com.tak8997.instastylegallery.ui

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

internal interface RecyclerItemTouchListener : RecyclerView.OnItemTouchListener {

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
}
