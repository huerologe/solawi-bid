package org.solyton.solawi.bid.application.exception

@Suppress("UnusedPrivateMember")
sealed class ApplicationException(override val message: String) : Exception(message) {
    data object MissingContextHeader: ApplicationException("Missing mandatory header: CONTEXT") {
        private fun readResolve(): Any = MissingContextHeader
    }
}
