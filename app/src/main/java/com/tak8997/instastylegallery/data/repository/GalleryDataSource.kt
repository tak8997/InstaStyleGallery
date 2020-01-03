package com.tak8997.instastylegallery.data.repository

import android.content.ContentResolver
import android.net.Uri
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
        MediaStore.Images.ImageColumns.DISPLAY_NAME,
        MediaStore.Images.ImageColumns.DATE_MODIFIED,
        MediaStore.Images.ImageColumns.MIME_TYPE,
        MediaStore.Images.ImageColumns.ORIENTATION
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

            val imageIdColNum = it.getColumnIndex(projections[0])
            val dateTakenColNum = it.getColumnIndex(projections[1])
            val bucketColNum = it.getColumnIndex(projections[2])
            val bucketIdColNum = it.getColumnIndex(projections[3])
            val imageNameColNum = it.getColumnIndex(projections[4])
            val dateModifiedColNum = it.getColumnIndexOrThrow(projections[5])
            val mimeTypeColNum = it.getColumnIndex(projections[6])
            val orientationColNum = it.getColumnIndexOrThrow(projections[7])

            while (!it.isAfterLast) {
                val imageId = it.getString(imageIdColNum)
                val imagePath = Uri.withAppendedPath(uri, imageId)
                val imageDate = it.getString(dateTakenColNum)
                val imageBucket = it.getString(bucketColNum)
                val bucketId = it.getString(bucketIdColNum)
                val imageName = it.getString(imageNameColNum)
                val dateModified = it.getLong(dateModifiedColNum)
                val mimeType = it.getString(mimeTypeColNum)
                val orientation = it.getInt(orientationColNum)

                galleryItems.add(
                    GalleryItem(
                        imageId,
                        imageName,
                        imageBucket,
                        imageDate,
                        bucketId,
                        imagePath,
                        dateModified,
                        mimeType,
                        orientation
                    )
                )

                it.moveToNext()
            }
        }
        cursor?.close()

        return galleryItems
    }
}
