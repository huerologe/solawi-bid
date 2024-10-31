package org.solyton.solawi.bid.application.routing

import org.solyton.solawi.bid.application.environment.Environment
import org.solyton.solawi.bid.module.health.routing.health
import org.solyton.solawi.bid.module.user.routing.user
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.solyton.solawi.bid.module.bid.routing.bid
import org.solyton.solawi.bid.module.user.data.api.User
import java.util.*

fun Application.setupRouting(environment: Environment) {
    routing {
        health(environment)
        user(environment)
        bid(environment)
    }
}

