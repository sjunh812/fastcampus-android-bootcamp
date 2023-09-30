package com.example.face_recongnition

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtil {

    fun checkPermission(context: Context, permissionList: List<String>): Boolean {
        permissionList.forEach { permission ->
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    fun requestPermission(activity: Activity, permissionList: List<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, permissionList.toTypedArray(), requestCode)
    }
}