package org.solyton.solawi.bid.module.bid.routing

import com.typesafe.config.ConfigFactory
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.utils.io.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.evoleq.ktorx.result.serializers
import org.junit.jupiter.api.Test
import org.solyton.solawi.bid.Api
import org.solyton.solawi.bid.application.solawiBid
import org.solyton.solawi.bid.module.bid.data.api.Bid
import org.solyton.solawi.bid.module.bid.routing.migrations.bidRoutingMigrations
import org.solyton.solawi.bid.module.db.migrations.dbMigrations
import java.io.File
import kotlin.test.assertTrue
import kotlin.test.fail

class RoutingTests {

    @Api@Test fun storeBid() = runBlocking{
        testApplication {
            environment {
                // Load the HOCON file explicitly with the file path
                val configFile = File("src/main/resources/application.conf")
                config = HoconApplicationConfig(ConfigFactory.parseFile(configFile))
                //config = HoconApplicationConfig(ConfigFactory.load(ConfigResolveOptions.defaults()))
            }
            application {
                 solawiBid(bidRoutingMigrations)
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
            assertTrue { response.status == HttpStatusCode.OK }
            val x = response.bodyAsText()
            assertTrue { x.isNotEmpty() }
        }
    }
}
