package org.solyton.solawi.bid.module.bid.routing

import com.typesafe.config.ConfigFactory
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.evoleq.ktorx.result.Result
import org.junit.jupiter.api.Test
import org.solyton.solawi.bid.Api
import org.solyton.solawi.bid.application.environment.setupEnvironment
import org.solyton.solawi.bid.application.pipeline.installDatabase
import org.solyton.solawi.bid.module.bid.data.api.Bid
import org.solyton.solawi.bid.module.bid.routing.migrations.bidRoutingMigrations
import org.solyton.solawi.bid.module.db.BidRoundException
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class RoutingTests {

    @Api@Test
    fun storeBid() = runBlocking{
        testApplication {
            environment {
                // Load the HOCON file explicitly with the file path
                val configFile = File("src/test/resources/bid.test.conf")
                config = HoconApplicationConfig(ConfigFactory.parseFile(configFile))

            }
            application {
                installDatabase(setupEnvironment(),bidRoutingMigrations)

            }
            val response = client.post("/bid/send") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(
                   Json.encodeToString(
                       Bid.serializer(),
                       Bid( "test-user", amount = 1.0, link = "test-link")
                   )
                )
            }
            assertTrue("Wrong status: ${response.status}"){response.status == HttpStatusCode.Conflict }
            val result = Json.decodeFromString(Result.Failure.Message.serializer(),response.bodyAsText())
            assertIs<Result.Failure.Message>(result)
            assertEquals(BidRoundException.RoundNotStarted.message, result.value)
        }
    }
}
