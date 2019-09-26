package com.wujia.businesslib

import android.Manifest
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.UserHandle
import android.text.TextUtils

import com.wujia.lib_common.utils.AppContext
import com.wujia.lib_common.utils.LogUtil

import java.lang.reflect.Constructor
import java.lang.reflect.Method
import java.util.Arrays

/**
 * Created by Administrator on 2018/3/7.
 */

object ThirdPermissionUtil {
    /**
     * PackageManager.FLAG_PERMISSION_USER_SET
     * PackageManager.FLAG_PERMISSION_USER_FIXED
     * PackageManager.FLAG_PERMISSION_POLICY_FIXED
     * PackageManager.FLAG_PERMISSION_REVOKE_ON_UPGRADE
     * PackageManager.FLAG_PERMISSION_SYSTEM_FIXED
     * PackageManager.FLAG_PERMISSION_GRANTED_BY_DEFAULT
     */
    val FLAG_PERMISSION_USER_SET = 1 shl 0
    val FLAG_PERMISSION_USER_FIXED = 1 shl 1
    val FLAG_PERMISSION_POLICY_FIXED = 1 shl 2
    val FLAG_PERMISSION_REVOKE_ON_UPGRADE = 1 shl 3
    val FLAG_PERMISSION_SYSTEM_FIXED = 1 shl 4
    val FLAG_PERMISSION_GRANTED_BY_DEFAULT = 1 shl 5

    private val grantPermissions = arrayOf(Manifest.permission.CHANGE_WIFI_MULTICAST_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.GET_TASKS, Manifest.permission.INTERNET, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE, Manifest.permission.VIBRATE, Manifest.permission.WAKE_LOCK, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS, Manifest.permission.BLUETOOTH, Manifest.permission.BROADCAST_STICKY, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_WIFI_STATE)
    private val grantList = Arrays.asList(*grantPermissions)

    @JvmOverloads
    fun requestDefaultPermissions(packageName: String, needGrant: Boolean = false) {
        if (TextUtils.isEmpty(packageName)) {
            return
        }

        try {
            val packageManager = AppContext.get().packageManager

            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            val applicationInfo = packageManager.getApplicationInfo(packageName, 0)

            val newUserHandle = UserHandle::class.java.getDeclaredConstructor(Int::class.javaPrimitiveType!!)
            newUserHandle.isAccessible = true
            val handle = newUserHandle.newInstance(0) as UserHandle

            val grantRuntimePermission = packageManager.javaClass.getDeclaredMethod("grantRuntimePermission", String::class.java, String::class.java, UserHandle::class.java)
            grantRuntimePermission.isAccessible = true

            val revokeRuntimePermission = packageManager.javaClass.getDeclaredMethod("revokeRuntimePermission", String::class.java, String::class.java, UserHandle::class.java)
            revokeRuntimePermission.isAccessible = true

            val updatePermissionFlags = packageManager.javaClass.getDeclaredMethod("updatePermissionFlags", String::class.java, String::class.java, Int::class.javaPrimitiveType, Int::class.javaPrimitiveType, UserHandle::class.java)
            updatePermissionFlags.isAccessible = true
            val permissions = info.requestedPermissions
            for (permission in permissions) {
                try {
                    if (grantList.contains(permission) || needGrant || applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM > 0) {
                        grantRuntimePermission.invoke(packageManager, packageName, permission, handle)
                    } else {
                        revokeRuntimePermission.invoke(packageManager, packageName, permission, handle)
                        /**
                         * 不再提示权限申请弹框
                         */
                        updatePermissionFlags.invoke(packageManager, permission, packageName,
                                FLAG_PERMISSION_USER_FIXED or FLAG_PERMISSION_USER_SET,
                                FLAG_PERMISSION_USER_FIXED, handle)
                    }
                } catch (e: Exception) {
                    LogUtil.i("Permission err : [ " + e.message + " ]")
                    e.printStackTrace()
                }

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
