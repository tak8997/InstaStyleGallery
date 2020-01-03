package com.tak8997.instastylegallery.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.signature.MediaStoreSignature
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
        if (galleryItem?.dateModified == null) {
            return
        }

//        custom cache key - url로 캐시키를 만드는데, url을 같은데 이미지는 다를 경우가 있을 수도 있음. 그럴 경우 방지를 위해 여러 경우의 수(mine, datemodified, orientation)를
//        포함해서 새로 키를 만들필요가 있음. 실제로 이미지가 바꼈을 경우만 새로운 캐시 키 가 생긴다.
        val signature = MediaStoreSignature(galleryItem.mimeType, galleryItem.dateModified, galleryItem.orientation)

        glide.load(galleryItem.imagePath)
            .signature(signature)
            .centerCrop()
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
