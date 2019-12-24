package com.tak8997.instastylegallery.data.repository

import androidx.loader.app.LoaderManager
import com.tak8997.instastylegallery.data.GalleryLoaderCallbacks

internal interface GalleryRepository {

    fun fetchGalleryItems(loaderManager: LoaderManager, callbacks: GalleryLoaderCallbacks)
}