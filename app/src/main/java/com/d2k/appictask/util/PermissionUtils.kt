package com.d2k.appictask.util

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import com.d2k.appictask.R

fun AppCompatActivity.isGranted(permission: AppPermission) = run {

    (PermissionChecker.checkSelfPermission(
        applicationContext, permission.permissionName
    ) == PermissionChecker.PERMISSION_GRANTED)

}

fun AppCompatActivity.shouldShowRationale(permission: AppPermission) = run {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        shouldShowRequestPermissionRationale(permission.permissionName)
    }
    else
        return true
}

fun AppCompatActivity.requestPermission(permission: AppPermission) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(
            arrayOf(permission.permissionName), permission.requestCode
        )
    }
}

fun AppCompatActivity.handlePermission(
    permission: AppPermission,
    onGranted: (AppPermission) -> Unit,
    onDenied: (AppPermission) -> Unit,
    onRationaleNeeded: ((AppPermission) -> Unit)? = null
) {
    when {
        isGranted(permission) -> onGranted.invoke(permission)
        shouldShowRationale(permission) -> onRationaleNeeded?.invoke(permission)
        else -> onDenied.invoke(permission)
    }
}

fun AppCompatActivity.handlePermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray,
    onPermissionGranted: (AppPermission) -> Unit,
    onPermissionDenied: ((AppPermission) -> Unit)? = null,
    onPermissionDeniedPermanently: ((AppPermission) -> Unit)? = null
) {

    AppPermission.permissions.find {
        it.requestCode == requestCode
    }?.let { appPermission ->
        val permissionGrantResult = mapPermissionsAndResults(
            permissions, grantResults
        )[appPermission.permissionName]
        when {
            PermissionChecker.PERMISSION_GRANTED == permissionGrantResult -> {
                onPermissionGranted(appPermission)
            }
            shouldShowRationale(appPermission) -> onPermissionDenied?.invoke(appPermission)
            else -> {
                goToAppDetailsSettings()
                onPermissionDeniedPermanently?.invoke(appPermission)
            }
        }
    }
}


private fun AppCompatActivity.goToAppDetailsSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", applicationContext.packageName, null)
    }
    startActivityForResult(intent,0)

}


private fun mapPermissionsAndResults(
    permissions: Array<out String>, grantResults: IntArray
): Map<String, Int> = permissions.mapIndexedTo(
    mutableListOf<Pair<String, Int>>()
) { index, permission -> permission to grantResults[index] }.toMap()


sealed class AppPermission(
    val permissionName: String,
    val requestCode: Int,
    val deniedMessageId: Int,
    val explanationMessageId: Int
) {
    companion object {
        val permissions: List<AppPermission> by lazy {
            listOf(
                ACCESS_STORAGE,
                ACCESS_CAMERA
            )
        }
    }


    object ACCESS_CAMERA : AppPermission(
        Manifest.permission.CAMERA, 2,
        R.string.permission_required_text, R.string.permission_required_text
    )

    object ACCESS_STORAGE : AppPermission(
        Manifest.permission.READ_EXTERNAL_STORAGE, 43,
        R.string.permission_required_text, R.string.permission_required_text
    )

}