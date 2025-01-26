package org.solyton.solawi.bid.module.permission

sealed class PermissionException(override val message: String) : Exception(message) {
    data object AccessDenied : PermissionException("Access denied")
}