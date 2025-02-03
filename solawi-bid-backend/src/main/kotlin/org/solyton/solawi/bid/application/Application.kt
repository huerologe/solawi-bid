package org.solyton.solawi.bid.application


import io.ktor.server.application.*
import org.solyton.solawi.bid.application.environment.setupEnvironment
import org.solyton.solawi.bid.application.pipeline.*
import org.solyton.solawi.bid.application.routing.setupRouting
import org.solyton.solawi.bid.module.db.migrations.dbMigrations

fun Application.solawiBid(test: Boolean = false) {
        val environment = setupEnvironment()
        if(!test) {
            val database = installDatabase(environment, dbMigrations)
            installUsers(environment, database)
        }
        installSerializers()
        installAuthentication(environment.jwt)
        installCors()
        installContentNegotiation()
        setupRouting(environment)

        interceptAndValidateHeaders()
}
