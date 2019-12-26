package com.tak8997.instastylegallery.ui

import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import javax.inject.Inject

internal class MainViewModel @Inject constructor(
    sharedViewModelDelegate: SharedViewModelDelegate
): ViewModel(), SharedViewModelDelegate by sharedViewModelDelegate {

    fun onRequestPermissionResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            MainActivity.REQUEST_PERMISSION_READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setPermissions(true)
                } else {
                    setPermissions(false)
                }
            }
        }
    }
}