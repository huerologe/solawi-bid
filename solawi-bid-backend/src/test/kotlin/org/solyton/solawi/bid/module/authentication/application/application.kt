package org.solyton.solawi.bid.module.authentication.application

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.solyton.solawi.bid.application.environment.setupEnvironment
import org.solyton.solawi.bid.application.pipeline.*
import org.solyton.solawi.bid.module.authentication.migrations.authenticationRoutingMigrations
import org.solyton.solawi.bid.module.authentication.routing.authentication
import org.solyton.solawi.bid.module.bid.routing.bid
import org.solyton.solawi.bid.module.bid.routing.migrations.bidRoutingMigrations
import org.solyton.solawi.bid.module.user.routing.user


fun Application.authenticationTest() {
    val environment = setupEnvironment()
    installDatabase(environment, authenticationRoutingMigrations)

    installDatabase(environment, bidRoutingMigrations)
    installSerializers()
    installAuthentication(environment.jwt)
    installCors()
    installContentNegotiation()
    routing {
        authentication(environment)
        user(environment){ this.it() }
        bid(environment){
            this.it()
        }
    }

}