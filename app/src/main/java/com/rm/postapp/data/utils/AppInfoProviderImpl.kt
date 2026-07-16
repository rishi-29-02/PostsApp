package com.rm.postapp.data.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.rm.postapp.domain.utils.AppInfoProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppInfoProviderImpl @Inject constructor(
    @param:ApplicationContext val context: Context
): AppInfoProvider {
    override fun getVersionName(): String {
        return try {
            val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.PackageInfoFlags.of(0)
                )
            } else {
                context.packageManager.getPackageInfo(context.packageName, 0)
            }
            packageInfo.versionName ?: "Unknown"
        } catch (e: Exception) {
            "Unknown"
        }
    }
}