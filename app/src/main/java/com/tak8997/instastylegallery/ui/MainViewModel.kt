package com.tak8997.instastylegallery.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

internal class MainViewModel @Inject constructor(

): ViewModel() {

    val permissionChecked = MutableLiveData<Boolean>(false)

    fun permissionChecked(permissionChecked: Boolean) {
        this.permissionChecked.value = permissionChecked
    }
}