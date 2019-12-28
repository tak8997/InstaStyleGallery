package com.tak8997.instastylegallery.ui.favorite

import androidx.lifecycle.ViewModel
import com.tak8997.instastylegallery.ui.SharedViewModelDelegate
import javax.inject.Inject

internal class FavoriteViewModel @Inject constructor(
    sharedViewModelDelegate: SharedViewModelDelegate
) : ViewModel(), SharedViewModelDelegate by sharedViewModelDelegate
