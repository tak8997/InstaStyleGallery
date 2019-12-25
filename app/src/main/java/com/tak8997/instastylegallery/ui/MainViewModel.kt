package com.tak8997.instastylegallery.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

internal open class MainViewModel @Inject constructor(

): ViewModel() {

    val permissionChecked = MutableLiveData<Boolean>(false)

    fun setPermissions(checked: Boolean) {
        permissionChecked.value = checked
    }
}