package com.tak8997.instastylegallery.data.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GalleryItem(
    val imageId: String,
    val imageName: String,
    val imageBucket: String,
    val imageDate: String,
    val bucketId: String,
    val imagePath: Uri,
    val dateModified: Long,
    val mimeType: String,
    val orientation: Int,
    var posX: Int = 0,
    var posY: Int = 0
) : Parcelable
