package com.tak8997.instastylegallery.data.repository

import android.content.ContentResolver
import androidx.lifecycle.MutableLiveData
import androidx.paging.Config
import androidx.paging.toLiveData
import com.tak8997.instastylegallery.data.model.GalleryItem
import com.tak8997.instastylegallery.data.model.GalleryListing
import javax.inject.Inject

internal class GalleryDataRepository @Inject constructor(
    private val contentResolver: ContentResolver
) : GalleryRepository {

    override fun fetchGalleryItems(permissionChecked: Boolean?, galleryItems: List<GalleryItem>?): GalleryListing<GalleryItem> {
        if (permissionChecked == null || permissionChecked == false) {
            return GalleryListing(MutableLiveData())
        }

        val dataSourceFactory = GalleryDataSourceFactory(contentResolver)

        val livePagedListBuilder = dataSourceFactory.toLiveData(Config(
            pageSize = 30,
            initialLoadSizeHint = 30,
            prefetchDistance = 10,
            enablePlaceholders = false
        ))

        return GalleryListing(livePagedListBuilder)
    }
}
