package com.tak8997.instastylegallery.ui

import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

internal interface SharedViewModelDelegate {

    val permissionChecked: MutableLiveData<Boolean>

    fun setPermissions(checked: Boolean)

    class ViewModel @Inject constructor() : SharedViewModelDelegate {

        override val permissionChecked = MutableLiveData<Boolean>(false)

        override fun setPermissions(checked: Boolean) {
            permissionChecked.value = checked
        }
    }
}
