package com.tak8997.instastylegallery.ui.gallery

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tak8997.instastylegallery.data.model.GalleryItem
import com.tak8997.instastylegallery.data.model.GalleryListing
import com.tak8997.instastylegallery.data.repository.GalleryRepository
import com.tak8997.instastylegallery.ui.SharedViewModelDelegate
import javax.inject.Inject

internal class GalleryViewModel @Inject constructor(
    private val repository: GalleryRepository,
    sharedViewModelDelegate: SharedViewModelDelegate
) : ViewModel(), SharedViewModelDelegate by sharedViewModelDelegate {

    private val pageResult = MutableLiveData<GalleryListing<GalleryItem>>()
    val transitionName = MutableLiveData<String>()
    val detailScene = MutableLiveData<Triple<View, String, GalleryItem?>>()

    val galleryItems = Transformations.switchMap(pageResult) {
        it.pages
    }

    fun onItemLongClick(itemView: View, transName: String, galleryItem: GalleryItem?) {
        transitionName.value = transName
        detailScene.value = Triple(itemView, transName, galleryItem)
    }

    fun fetchGalleryItems() {
        pageResult.value = repository.fetchGalleryItems(permissionChecked.value, galleryItems.value)
    }
}
