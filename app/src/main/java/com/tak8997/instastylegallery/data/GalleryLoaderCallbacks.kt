package com.tak8997.instastylegallery.data

import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.tak8997.instastylegallery.GalleryApp
import com.tak8997.instastylegallery.data.model.GalleryItem

internal abstract class GalleryLoaderCallbacks : LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        const val ID_LOAD_GALLERY = 0
    }

    private val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    private val projections = arrayOf(
        MediaStore.Images.ImageColumns._ID,
        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
        MediaStore.Images.ImageColumns.BUCKET_ID,
        MediaStore.Images.ImageColumns.DISPLAY_NAME
    )
    private var sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC"

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            GalleryApp.instance,
            uri,
            projections,
            null,
            null,
            sortOrder
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data == null) {
            return
        }

        val galleryItems = mutableListOf<GalleryItem>()

        while (data.moveToNext()) {
            val imagePath = uri.toString() + "/" + data.getString(0)
            val imageBucket = data.getString(data.getColumnIndex(projections[1]))
            val bucketId = data.getString(data.getColumnIndex(projections[2]))
            val imageName = data.getString(data.getColumnIndex(projections[3]))

            galleryItems.add(
                GalleryItem(
                    imageName,
                    imageBucket,
                    bucketId,
                    imagePath
                )
            )
        }

        onGalleryItemsLoaded(galleryItems)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
    }

    abstract fun onGalleryItemsLoaded(galleryItems: List<GalleryItem>)
}
