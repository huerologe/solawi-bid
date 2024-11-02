package org.evoleq.exposedx.transaction

import org.evoleq.exposedx.NoMessageProvided
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.evoleq.ktorx.result.Result

fun <T : Any> resultTransaction(database: Database, statement: Transaction.() -> T): Result<T> = transaction(database) {
    try {
        Result.Success(statement())
    } catch (e: Throwable) {
        Result.Failure.Exception(e)
    }
}