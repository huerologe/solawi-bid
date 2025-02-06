package org.solyton.solawi.bid.module.bid.application

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.solyton.solawi.bid.application.environment.setupEnvironment
import org.solyton.solawi.bid.application.pipeline.installContentNegotiation
import org.solyton.solawi.bid.application.pipeline.installCors
import org.solyton.solawi.bid.application.pipeline.installDatabase
import org.solyton.solawi.bid.application.pipeline.installSerializers
import org.solyton.solawi.bid.module.authentication.migrations.authenticationMigrations
import org.solyton.solawi.bid.module.bid.routing.auction
import org.solyton.solawi.bid.module.bid.routing.sendBid
import org.solyton.solawi.bid.module.bid.routing.bid
import org.solyton.solawi.bid.module.bid.routing.migrations.bidRoutingMigrations
import org.solyton.solawi.bid.module.bid.routing.round


fun Application.bidTest() {
    val environment = setupEnvironment()
    installDatabase(environment, bidRoutingMigrations)
    installDatabase(environment, authenticationMigrations)
    installSerializers()
    installCors()
    installContentNegotiation()
    routing {
        sendBid(environment)
        bid(environment){
            this.it()
        }
        auction(environment) {
            this.it()
        }
        round(environment){
            this.it()
        }
    }
}