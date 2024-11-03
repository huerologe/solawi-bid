package org.solyton.solawi.bid.module.authentication.application

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.solyton.solawi.bid.application.environment.setupEnvironment
import org.solyton.solawi.bid.application.pipeline.*
import org.solyton.solawi.bid.module.authentication.migrations.authenticationRoutingMigrations
import org.solyton.solawi.bid.module.authentication.routing.authentication


var count = 0
fun Application.authenticationTest() {
    val environment = setupEnvironment()
    installDatabase(environment, authenticationRoutingMigrations)

    installSerializers()
    installAuthentication(environment.jwt)
    installCors()
    installContentNegotiation()
    routing {
        authentication(environment)
    }

}