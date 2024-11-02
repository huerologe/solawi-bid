package org.solyton.solawi.bid.module.user.exception

sealed class UserManagementException(override val message: String?) : Exception(message) {
    data class UserDoesNotExist(val username: String) : UserManagementException("User $username does not exists")
    data object WrongCredentials : UserManagementException("Wrong credentials")
}