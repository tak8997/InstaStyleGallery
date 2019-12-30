package com.tak8997.instastylegallery.data.repository

import android.content.ContentResolver
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
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

        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setInitialLoadSizeHint(30)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        return GalleryListing(
            pages = LivePagedListBuilder(dataSourceFactory, config).build()
        )
    }
}
