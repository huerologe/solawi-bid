package org.solyton.solawi.bid.module.permission.action.db

import org.jetbrains.exposed.sql.Transaction
import java.util.UUID

fun Transaction.isGranted(userId: UUID, context: String, right: String) {
    TODO()
}