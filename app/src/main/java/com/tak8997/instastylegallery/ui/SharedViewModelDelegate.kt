package com.tak8997.instastylegallery.ui

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.tak8997.instastylegallery.data.model.GalleryItem
import com.tak8997.instastylegallery.data.model.Quadruple
import javax.inject.Inject

internal interface SharedViewModelDelegate {

    val permissionChecked: MutableLiveData<Boolean>
    val detailGalleryView: MutableLiveData<Quadruple<Boolean?, String?, View?, GalleryItem?>>

    fun setPermissions(checked: Boolean)

    fun openGalleryDetail(
        action: Int,
        itemView: View,
        transitionName: String,
        galleryItem: GalleryItem?
    )

    fun closeGalleryDetail()

    class ViewModel @Inject constructor() : SharedViewModelDelegate {

        private val handler = Handler(Looper.getMainLooper())
        private val transitionName = MutableLiveData<String>()

        override val permissionChecked = MutableLiveData<Boolean>(false)
        override val detailGalleryView = MutableLiveData<Quadruple<Boolean?, String?, View?, GalleryItem?>>()

        override fun setPermissions(checked: Boolean) {
            permissionChecked.value = checked
        }

        override fun openGalleryDetail(
            action: Int,
            itemView: View,
            transitionName: String,
            galleryItem: GalleryItem?
        ) {

            handler.postDelayed({
                detailGalleryView.value = Quadruple(true, transitionName, itemView, galleryItem)
            }, 600)
            this.transitionName.value = transitionName
        }

        override fun closeGalleryDetail() {
            detailGalleryView.value = Quadruple(false, transitionName.value)
        }
    }
}
