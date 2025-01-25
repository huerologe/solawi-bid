package org.solyton.solawi.bid.application


import io.ktor.server.application.*
import org.solyton.solawi.bid.application.environment.setupEnvironment
import org.solyton.solawi.bid.application.pipeline.*
import org.solyton.solawi.bid.application.routing.setupRouting
import org.solyton.solawi.bid.module.db.migrations.dbMigrations

var setupDone = false

fun Application.solawiBid(test: Boolean = false) {
    //if(!setupDone) {
        val environment = setupEnvironment()
        if(!test) {
            installDatabase(environment, dbMigrations)
        }
        installSerializers()
        installAuthentication(environment.jwt)
        installCors()
        installContentNegotiation()
        setupRouting(environment)

        interceptAndValidateHeaders()

        setupDone = true
    // }
}