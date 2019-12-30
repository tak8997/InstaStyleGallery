package com.tak8997.instastylegallery.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.tak8997.instastylegallery.GlideRequests
import com.tak8997.instastylegallery.R
import com.tak8997.instastylegallery.data.model.GalleryItem

internal class GalleryImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attributeSet, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth)
    }

    fun setImageThumbnail(galleryItem: GalleryItem?, glide: GlideRequests) {
        glide.load(galleryItem?.imagePath)
            .centerCrop()
            .placeholder(circularProgressDrawable(context))
            .thumbnail(0.4f)
            .into(this)
    }

    private fun circularProgressDrawable(context: Context): CircularProgressDrawable {
        val progress = CircularProgressDrawable(context)
        progress.setColorSchemeColors(R.color.colorAccent)
        progress.strokeWidth = 5f
        progress.centerRadius = 30f
        progress.start()
        return progress
    }
}
