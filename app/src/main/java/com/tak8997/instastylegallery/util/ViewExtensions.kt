package com.tak8997.instastylegallery.util

import android.content.res.Resources
import android.graphics.Point
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

fun View.locationOnScreen(): Point {
    val location = IntArray(2)
    getLocationOnScreen(location)
    return Point(location[0], location[1])
}

fun View.locationInWindow(): Point {
    val location = IntArray(2)
    getLocationInWindow(location)
    return Point(location[0], location[1])
}

fun FragmentActivity.replaceFragment(fragment: Fragment, tag: String = "", frameId: Int) =
        supportFragmentManager
    .beginTransaction()
    .replace(frameId, fragment)
    .commit()
