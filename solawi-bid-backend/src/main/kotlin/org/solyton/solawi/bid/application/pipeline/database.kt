package org.solyton.solawi.bid.application.pipeline

import io.ktor.server.application.*
import kotlinx.coroutines.runBlocking
import org.evoleq.exposedx.migrations.Migration
import org.evoleq.exposedx.migrations.runOn
import org.jetbrains.exposed.sql.Database
import org.solyton.solawi.bid.application.environment.Environment
import org.solyton.solawi.bid.application.environment.connectToDatabase

fun Application.installDatabase(environment: Environment,migrations: ArrayList<Database.()-> Migration> = arrayListOf()) = runBlocking {
    migrations.runOn(environment.connectToDatabase())
}