package com.tak8997.instastylegallery.util

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

fun FragmentActivity.replaceFragment(fragment: Fragment, tag: String = "", frameId: Int)
        = supportFragmentManager
    .beginTransaction()
    .replace(frameId, fragment)
    .commit()