package org.solyton.solawi.bid.module.db

import java.util.*


sealed class ContextException (override val message: String) : Exception(message) {
    data class NoSuchChildContext(val id: UUID) : UserManagementException("No such child context; id = $id")
}