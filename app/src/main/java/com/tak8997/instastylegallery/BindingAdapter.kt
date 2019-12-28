package com.tak8997.instastylegallery

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

internal object BindingAdapter {

    @JvmStatic
    @BindingAdapter("srcCompat", "thumbnail", requireAll = false)
    fun setSrcCompat(imageView: ImageView, imageUrl: String?, thumbnail: Boolean = false) {
        imageUrl?.let {
            Glide
                .with(imageView.context)
                .load(it)
                .placeholder(circularProgressDrawable(imageView.context))
                .thumbnail(if (thumbnail) 0.4f else 1f)
                .into(imageView)
        }
    }

    @JvmStatic
    fun circularProgressDrawable(context: Context): CircularProgressDrawable {
        val progress = CircularProgressDrawable(context)
        progress.setColorSchemeColors(R.color.colorAccent)
        progress.strokeWidth = 5f
        progress.centerRadius = 30f
        progress.start()
        return progress
    }
}
