package org.evoleq.util

import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.*
import org.evoleq.math.x

@KtorDsl
@Suppress("FunctionName")
suspend inline fun <reified T : Any>  Receive(): Action<T> = ApiAction {
        call-> call.receive<T>() x call
}

@KtorDsl
@Suppress("FunctionName")
suspend inline fun <reified T : Any>  Respond(): KlAction<T, Unit> = {t -> ApiAction {
        call-> call.respond(t) x call
} }