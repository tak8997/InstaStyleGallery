package com.tak8997.instastylegallery.data.repository

import androidx.loader.app.LoaderManager
import com.tak8997.instastylegallery.data.GalleryItem
import com.tak8997.instastylegallery.data.GalleryLoaderCallbacks
import javax.inject.Inject

internal class GalleryDataRepository @Inject constructor() : GalleryRepository {

    override fun fetchGalleryItems(loaderManager: LoaderManager, permissionChecked: Boolean?, galleryItems: List<GalleryItem>?, callbacks: GalleryLoaderCallbacks) {
        if (permissionChecked == null || permissionChecked == false) {
            return
        }

        if (galleryItems.isNullOrEmpty()) {
            loaderManager.initLoader(GalleryLoaderCallbacks.ID_LOAD_GALLERY, null, callbacks)
        } else {
            loaderManager.restartLoader(GalleryLoaderCallbacks.ID_LOAD_GALLERY, null, callbacks)
        }
    }
}
