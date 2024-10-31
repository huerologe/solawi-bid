package org.solyton.solawi.bid.module.user.routing

import org.solyton.solawi.bid.application.environment.Environment
import org.solyton.solawi.bid.module.user.action.getAllUsers
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.evoleq.math.state.runOn
import org.evoleq.util.Base

@KtorDsl
fun Routing.user(environment: Environment) {
    get("users/all") {
        getAllUsers() runOn Base(call, environment)
    }
}