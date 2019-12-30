package com.tak8997.instastylegallery.data.model

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class GalleryListing<T>(
    val pages: LiveData<PagedList<T>>
)
