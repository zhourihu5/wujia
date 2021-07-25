package com.wujia.lib_common.utils.grant

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference
import java.util.*

/**
 * A class to help you manage your permissions simply.
 */
class PermissionsManager private constructor() {

    private val mPendingRequests = HashSet<String>(1)
    private val mPermissions = HashSet<String>(1)
    private val mPendingActions = ArrayList<WeakReference<PermissionsResultAction>>(1)

    init {
        initializePermissionsMap()
    }

    /**
     * This method uses reflection to read all the permissions in the Manifest class.
     * This is necessary because some permissions do not exist on older versions of Android,
     * since they do not exist, they will be denied when you check whether you have permission
     * which is problematic since a new permission is often added where there was no previous
     * permission required. We initialize a Set of available permissions and check the set
     * when checking if we have permission since we want to know when we are denied a permission
     * because it doesn't exist yet.
     */
    @Synchronized
    private fun initializePermissionsMap() {
        val fields = Manifest.permission::class.java.fields
        for (field in fields) {
            var name: String? = null
            try {
                name = field.get("") as String
            } catch (e: IllegalAccessException) {
                Log.e(TAG, "Could not access field", e)
            }

            name?.let { mPermissions.add(it) }
        }
    }

    /**
     * This method retrieves all the permissions declared in the application's manifest.
     * It returns a non null array of permisions that can be declared.
     *
     * @param activity the Activity necessary to check what permissions we have.
     * @return a non null array of permissions that are declared in the application manifest.
     */
    @Synchronized
    private fun getManifestPermissions(activity: Activity): Array<String> {
        var packageInfo: PackageInfo? = null
        val list = ArrayList<String>(1)
        try {
            Log.d(TAG, activity.packageName)
            packageInfo = activity.packageManager.getPackageInfo(activity.packageName, PackageManager.GET_PERMISSIONS)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "A problem occurred when retrieving permissions", e)
        }

        if (packageInfo != null) {
            val permissions = packageInfo.requestedPermissions
            if (permissions != null) {
                for (perm in permissions) {
                    Log.d(TAG, "Manifest contained permission: $perm")
                    list.add(perm)
                }
            }
        }
        return list.toTypedArray()
    }

    /**
     * This method adds the [PermissionsResultAction] to the current list
     * of pending actions that will be completed when the permissions are
     * received. The list of permissions passed to this method are registered
     * in the PermissionsResultAction object so that it will be notified of changes
     * made to these permissions.
     *
     * @param permissions the required permissions for the action to be executed.
     * @param action      the action to add to the current list of pending actions.
     */
    @Synchronized
    private fun addPendingAction(permissions: Array<String>,
                                 action: PermissionsResultAction?) {
        if (action == null) {
            return
        }
        action.registerPermissions(permissions)
        mPendingActions.add(WeakReference(action))
    }

    /**
     * This method removes a pending action from the list of pending actions.
     * It is used for cases where the permission has already been granted, so
     * you immediately wish to remove the pending action from the queue and
     * execute the action.
     *
     * @param action the action to remove
     */
    @Synchronized
    private fun removePendingAction(action: PermissionsResultAction?) {
        val iterator = mPendingActions.iterator()
        while (iterator.hasNext()) {
            val weakRef = iterator.next()
            if (weakRef.get() === action || weakRef.get() == null) {
                iterator.remove()
            }
        }
    }

    /**
     * This static method can be used to check whether or not you have a specific permission.
     * It is basically a less verbose method of using [ActivityCompat.checkSelfPermission]
     * and will simply return a boolean whether or not you have the permission. If you pass
     * in a null Context object, it will return false as otherwise it cannot check the permission.
     * However, the Activity parameter is nullable so that you can pass in a reference that you
     * are not always sure will be valid or not (e.g. getActivity() from Fragment).
     *
     * @param context    the Context necessary to check the permission
     * @param permission the permission to check
     * @return true if you have been granted the permission, false otherwise
     */
    @Synchronized
    fun hasPermission(context: Context?, permission: String): Boolean {
        return context != null && (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED || !mPermissions.contains(permission))
    }

    /**
     * This method should be used to execute a [PermissionsResultAction] for the array
     * of permissions passed to this method. This method will request the permissions if
     * they need to be requested (i.e. we don't have permission yet) and will add the
     * PermissionsResultAction to the queue to be notified of permissions being granted or
     * denied. In the case of pre-Android Marshmallow, permissions will be granted immediately.
     * The Activity variable is nullable, but if it is null, the method will fail to execute.
     * This is only nullable as a courtesy for Fragments where getActivity() may yeild null
     * if the Fragment is not currently added to its parent Activity.
     *
     * @param activity    the activity necessary to request the permissions.
     * @param permissions the list of permissions to request for the [PermissionsResultAction].
     * @param action      the PermissionsResultAction to notify when the permissions are granted or denied.
     */
    @Synchronized
    fun requestPermissionsIfNecessaryForResult(activity: Activity?,
                                               permissions: Array<String>,
                                               action: PermissionsResultAction?) {
        if (activity == null) {
            return
        }
        addPendingAction(permissions, action)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            doPermissionWorkBeforeAndroidM(activity, permissions, action)
        } else {
            val permList = getPermissionsListToRequest(activity, permissions, action)
            if (permList.isEmpty()) {
                //if there is no permission to request, there is no reason to keep the action int the list
                removePendingAction(action)
            } else {
                val permsToRequest = permList.toTypedArray()
                mPendingRequests.addAll(permList)
                ActivityCompat.requestPermissions(activity, permsToRequest, 1)
            }
        }
    }

    /**
     * When request permissions on devices before Android M (Android 6.0, API Level 23)
     * Do the granted or denied work directly according to the permission status
     *
     * @param activity    the activity to check permissions
     * @param permissions the permissions names
     * @param action      the callback work object, containing what we what to do after
     * permission check
     */
    private fun doPermissionWorkBeforeAndroidM(activity: Activity,
                                               permissions: Array<String>,
                                               action: PermissionsResultAction?) {
        for (perm in permissions) {
            if (action != null) {
                if (!mPermissions.contains(perm)) {
                    action.onResult(perm, Permissions.NOT_FOUND)
                } else if (ActivityCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED) {
                    action.onResult(perm, Permissions.DENIED)
                } else {
                    action.onResult(perm, Permissions.GRANTED)
                }
            }
        }
    }

    /**
     * Filter the permissions list:
     * If a permission is not granted, add it to the result list
     * if a permission is granted, do the granted work, do not add it to the result list
     *
     * @param activity    the activity to check permissions
     * @param permissions all the permissions names
     * @param action      the callback work object, containing what we what to do after
     * permission check
     * @return a list of permissions names that are not granted yet
     */
    private fun getPermissionsListToRequest(activity: Activity,
                                            permissions: Array<String>,
                                            action: PermissionsResultAction?): List<String> {
        val permList = ArrayList<String>(permissions.size)
        for (perm in permissions) {
            if (!mPermissions.contains(perm)) {
                action?.onResult(perm, Permissions.NOT_FOUND)
            } else if (ActivityCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED) {
                if (!mPendingRequests.contains(perm)) {
                    permList.add(perm)
                }
            } else {
                action?.onResult(perm, Permissions.GRANTED)
            }
        }
        return permList
    }

    companion object {

        private val TAG = PermissionsManager::class.java.simpleName

        private var mInstance: PermissionsManager? = null

        val instance: PermissionsManager
            get() {
                if (mInstance == null) {
                    mInstance = PermissionsManager()
                }
                return mInstance!!
            }
    }

}
