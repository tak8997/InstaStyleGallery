package com.tak8997.instastylegallery.data.repository

import android.content.ContentResolver
import android.provider.MediaStore
import androidx.paging.PositionalDataSource
import com.tak8997.instastylegallery.data.model.GalleryItem

internal class GalleryDataSource(
    private val contentResolver: ContentResolver
) : PositionalDataSource<GalleryItem>() {

    private val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    private val projections = arrayOf(
        MediaStore.Images.ImageColumns._ID,
        MediaStore.Images.ImageColumns.DATE_TAKEN,
        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
        MediaStore.Images.ImageColumns.BUCKET_ID,
        MediaStore.Images.ImageColumns.DISPLAY_NAME
    )

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<GalleryItem>) {
        callback.onResult(getImages(params.loadSize, params.startPosition))
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<GalleryItem>) {
        callback.onResult(getImages(params.requestedLoadSize, params.requestedStartPosition), 0)
    }

    private fun getImages(limit: Int, offset: Int): MutableList<GalleryItem> {
        val cursor = contentResolver.query(
            uri,
            projections,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC LIMIT " + limit + " OFFSET " + offset
        )

        val galleryItems = mutableListOf<GalleryItem>()
        cursor?.let {

            it.moveToFirst()
            while (!it.isAfterLast) {
                val imagePath = uri.toString() + "/" + it.getString(0)
                val imageDate = it.getString(it.getColumnIndex(projections[1]))
                val imageBucket = it.getString(it.getColumnIndex(projections[2]))
                val bucketId = it.getString(it.getColumnIndex(projections[3]))
                val imageName = it.getString(it.getColumnIndex(projections[4]))

                galleryItems.add(
                    GalleryItem(
                        imageName,
                        imageBucket,
                        bucketId,
                        imagePath
                    )
                )

                it.moveToNext()
            }
        }
        cursor?.close()

        return galleryItems
    }
}
