package com.tak8997.instastylegallery.ui.gallerydetail

import androidx.lifecycle.ViewModel
import com.tak8997.instastylegallery.ui.SharedViewModelDelegate
import javax.inject.Inject

internal class GalleryDetailViewModel @Inject constructor(
    sharedViewModelDelegate: SharedViewModelDelegate
) : ViewModel(), SharedViewModelDelegate by sharedViewModelDelegate
