package com.tak8997.instastylegallery.data.repository

import com.tak8997.instastylegallery.data.model.GalleryItem
import com.tak8997.instastylegallery.data.model.GalleryListing

internal interface GalleryRepository {

    fun fetchGalleryItems(permissionChecked: Boolean?, galleryItems: List<GalleryItem>?): GalleryListing<GalleryItem>
}
