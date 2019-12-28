package com.tak8997.instastylegallery

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

internal class PermissionsUtil {

    companion object {

        fun isPermissionGranted(context: Context, permission: String): Boolean {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }
}
