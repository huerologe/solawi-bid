package org.solyton.solawi.bid.application


import org.solyton.solawi.bid.application.environment.setupEnvironment
import org.solyton.solawi.bid.application.routing.setupRouting
import io.ktor.server.application.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.serializer
import org.evoleq.exposedx.migrations.Migration
import org.evoleq.exposedx.migrations.runOn
import org.evoleq.ktorx.result.Result
import org.evoleq.ktorx.result.ResultSerializer
import org.evoleq.ktorx.result.serializers
import org.jetbrains.exposed.sql.Database
import org.solyton.solawi.bid.application.environment.connectToDatabase
import org.solyton.solawi.bid.application.pipeline.installAuthentication
import org.solyton.solawi.bid.application.pipeline.installContentNegotiation
import org.solyton.solawi.bid.application.pipeline.installCors
import org.solyton.solawi.bid.module.bid.data.api.Bid
import org.solyton.solawi.bid.module.authentication.data.api.LoggedIn
import org.solyton.solawi.bid.module.authentication.data.api.Login
import org.solyton.solawi.bid.module.authentication.data.api.RefreshToken

var setupDone = false

fun Application.solawiBid(migrations: ArrayList<Database.()-> Migration> = arrayListOf()) {
    if(!setupDone) {


        // primitive serializers
        serializers[Int::class] = Int.serializer()
        serializers[Boolean::class] = Boolean.serializer()
        serializers[String::class] = String.serializer()
        serializers[Double::class] = Double.serializer()
        // Result serializers
        serializers[Result::class] = ResultSerializer
        serializers[Result.Success::class] = ResultSerializer
        serializers[Result.Failure::class] = ResultSerializer
        serializers[Result.Failure.Message::class] = ResultSerializer
        serializers[Result.Failure.Exception::class] = ResultSerializer

        // Login serializers
        serializers[Login::class] = Login.serializer()
        serializers[LoggedIn::class] = LoggedIn.serializer()
        serializers[RefreshToken::class] = RefreshToken.serializer()
        // Bid serializers
        serializers[Bid::class] = Bid.serializer()

        val environment = setupEnvironment()


        installAuthentication(environment.jwt)
        installCors()
        installContentNegotiation()
        setupRouting(environment)

        runBlocking {
            migrations.runOn(environment.connectToDatabase())
        }
        setupDone = true
    }
}