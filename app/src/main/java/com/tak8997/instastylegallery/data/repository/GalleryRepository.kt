package com.tak8997.instastylegallery.data.repository

import androidx.loader.app.LoaderManager
import com.tak8997.instastylegallery.data.GalleryItem
import com.tak8997.instastylegallery.data.GalleryLoaderCallbacks

internal interface GalleryRepository {

    fun fetchGalleryItems(loaderManager: LoaderManager, permissionChecked: Boolean?, galleryItems: List<GalleryItem>?, callbacks: GalleryLoaderCallbacks)
}
