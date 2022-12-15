package com.streamwide.mycontacts.utils

import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object PermissionTracking {

    fun hasCOntactPermissions(context: Context):Boolean =
        EasyPermissions.hasPermissions(
            context,
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
        )
}