package com.tak8997.instastylegallery.data.repository

import androidx.loader.app.LoaderManager
import com.tak8997.instastylegallery.data.GalleryLoaderCallbacks
import javax.inject.Inject

internal class GalleryDataRepository @Inject constructor(): GalleryRepository {

    override fun fetchGalleryItems(loaderManager: LoaderManager, callbacks: GalleryLoaderCallbacks) {
        loaderManager.initLoader(GalleryLoaderCallbacks.ID_LOAD_GALLERY, null, callbacks)
    }
}