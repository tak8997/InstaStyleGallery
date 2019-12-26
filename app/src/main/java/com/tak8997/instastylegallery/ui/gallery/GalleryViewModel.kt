package com.tak8997.instastylegallery.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.loader.app.LoaderManager
import com.tak8997.instastylegallery.data.GalleryItem
import com.tak8997.instastylegallery.data.GalleryLoaderCallbacks
import com.tak8997.instastylegallery.data.repository.GalleryRepository
import com.tak8997.instastylegallery.ui.MainViewModel
import com.tak8997.instastylegallery.ui.SharedViewModelDelegate
import javax.inject.Inject

internal class GalleryViewModel @Inject constructor(
    private val repository: GalleryRepository,
    sharedViewModelDelegate: SharedViewModelDelegate
) : ViewModel(), SharedViewModelDelegate by sharedViewModelDelegate {

    val galleryItems = MutableLiveData<List<GalleryItem>>()

    fun fetchGalleryItems(loaderManager: LoaderManager) {
        if (permissionChecked.value == false) {
            return
        }

        repository.fetchGalleryItems(loaderManager, object : GalleryLoaderCallbacks() {

            override fun onGalleryItemsLoaded(galleryItems: List<GalleryItem>) {
                this@GalleryViewModel.galleryItems.value = galleryItems
            }
        })
    }
}