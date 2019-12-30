package com.tak8997.instastylegallery.data.repository

import android.content.ContentResolver
import androidx.paging.DataSource
import com.tak8997.instastylegallery.data.model.GalleryItem

internal class GalleryDataSourceFactory(
    private val contentResolver: ContentResolver
) : DataSource.Factory<Int, GalleryItem>() {

    override fun create(): DataSource<Int, GalleryItem> {
        return GalleryDataSource(contentResolver)
    }
}
