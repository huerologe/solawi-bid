package org.solyton.solawi.bid.application.environment

import io.ktor.server.application.*


fun Application.setupEnvironment(): Environment = with(environment.config){
    val database = Database(
        url = property("database.url").getString(),
        driver = property("database.driver").getString(),
        user = property("database.user").getString(),
        password = property("database.password").getString()
    )

    val jwt = JWT(
        domain = property("jwt.domain").getString(),
        audience = property("jwt.audience").getString(),
        realm = property("jwt.realm").getString(),
        secret = property("jwt.secret").getString(),
    )

    Environment(
        database,
        jwt,
    )
}

fun Environment.connectToDatabase(): org.jetbrains.exposed.sql.Database = org.jetbrains.exposed.sql.Database.connect(
    database.url,
    database.driver,
    database.user,
    database.password
)

data class Environment(
    val database: Database,
    val jwt: JWT
)
data class JWT(
    val domain:String, // issuer
    val audience: String,
    val realm: String,
    val secret: String
)
data class Database(
    val url: String,
    val driver: String,
    val user: String,
    val password: String
)

