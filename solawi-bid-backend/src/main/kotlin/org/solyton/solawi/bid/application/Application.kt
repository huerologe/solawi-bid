package org.solyton.solawi.bid.application


import org.solyton.solawi.bid.application.environment.setupEnvironment
import org.solyton.solawi.bid.application.routing.setupRouting
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.serializer
import org.evoleq.exposedx.migrations.Migration
import org.evoleq.exposedx.migrations.runOn
import org.evoleq.ktorx.result.Result
import org.evoleq.ktorx.result.ResultSerializer
import org.evoleq.ktorx.result.serializers
import org.jetbrains.exposed.sql.Database
import org.solyton.solawi.bid.application.environment.connectToDatabase
import org.solyton.solawi.bid.module.bid.data.api.Bid


fun Application.solawiBid(migrations: ArrayList<Database.()-> Migration> = arrayListOf()) {

    try {
        install(CORS) {
            allowMethod(HttpMethod.Options)
            allowMethod(HttpMethod.Get)
            allowMethod(HttpMethod.Post)
            allowMethod(HttpMethod.Put)
            allowMethod(HttpMethod.Delete)
            allowMethod(HttpMethod.Patch)
            allowHeader(HttpHeaders.Authorization)
            allowHeader(HttpHeaders.AccessControlAllowHeaders)
            allowHeader(HttpHeaders.ContentType)
            allowHeader(HttpHeaders.AccessControlAllowOrigin)
            allowHeader(HttpHeaders.AccessControlAllowMethods)
            allowCredentials = true
            anyHost()
            maxAgeInSeconds = 24 * 3600 // 1 day
        }

    install(ContentNegotiation) {
        json()
    }
    } catch (_:Exception) {}


    serializers[Int::class] = Int.serializer()
    serializers[String::class] = String.serializer()
    serializers[Result::class] = ResultSerializer
    serializers[Result.Success::class] = ResultSerializer
    serializers[Result.Failure::class] = ResultSerializer

    serializers[Bid::class] = Bid.serializer()

    val environment = setupEnvironment()
    runBlocking {
        migrations.runOn(environment.connectToDatabase())
    }
    setupRouting(environment)
}