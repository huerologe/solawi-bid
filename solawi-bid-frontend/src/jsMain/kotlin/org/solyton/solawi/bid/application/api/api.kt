package org.solyton.solawi.bid.application.api

import org.evoleq.ktorx.result.Result
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.module.authentication.data.api.LoggedIn
import org.solyton.solawi.bid.module.authentication.data.api.Login
import org.solyton.solawi.bid.application.action.KlAppState


data class Api(
    private val api: Map<String, Application.(suspend (Login)-> LoggedIn)->Unit>
) : Map<String, Application.(suspend (Login)-> LoggedIn)->Unit> by api

val login: Application.(suspend (Login)-> LoggedIn)->Unit = TODO()

val api = mapOf(
    "login" to login
)

val Login: KlAppState<Login, Result<LoggedIn>> = TODO()