package com.wpfl5.chattutorial.ext

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Context.getAppInfoIntent(packageName: String = this.packageName): Intent =
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }

fun Context.goToAppInfoPage(packageName: String = this.packageName) {
    startActivity(getAppInfoIntent(packageName))
}