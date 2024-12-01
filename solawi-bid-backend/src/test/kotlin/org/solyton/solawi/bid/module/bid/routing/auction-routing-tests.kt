package org.solyton.solawi.bid.module.bid.routing

import com.typesafe.config.ConfigFactory
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.solyton.solawi.bid.Api
import org.solyton.solawi.bid.module.bid.data.api.CreateAuction
import java.io.File
import kotlin.test.assertTrue

class AuctionRoutingTests {

    @Api@Test
    fun createAuction() = runBlocking{
        testApplication {
            environment {
                // Load the HOCON file explicitly with the file path
                val configFile = File("src/test/resources/bid.api.test.conf")
                config = HoconApplicationConfig(ConfigFactory.parseFile(configFile))

            }
            application {

            }
            val response = client.post("/auction/create") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(
                    Json.encodeToString(
                        CreateAuction.serializer(),
                        CreateAuction("test-name", LocalDate(1,1,1))
                    )
                )
            }

            assertTrue("Wrong status: ${response.status}, expected ${HttpStatusCode.OK}"){response.status == HttpStatusCode.OK }
            //val result = Json.decodeFromString(Result.Failure.Message.serializer(),response.bodyAsText())
            //assertIs<Result.Failure.Message>(result)
            //assertEquals(BidRoundException.RoundNotStarted.message, result.value)
        }
    }
}