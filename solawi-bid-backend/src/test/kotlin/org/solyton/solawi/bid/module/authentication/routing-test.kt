package org.solyton.solawi.bid.module.authentication

import com.typesafe.config.ConfigFactory
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.config.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.evoleq.ktorx.result.Result
import org.evoleq.ktorx.result.ResultSerializer
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.solyton.solawi.bid.Api
import org.solyton.solawi.bid.module.authentication.data.api.LoggedIn
import org.solyton.solawi.bid.module.authentication.data.api.Login
import java.io.File
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class RoutingTest {

    @Api@ParameterizedTest
    @ValueSource(strings = [
        "before-login",
        "login-with-wrong-username",
        "login-with-wrong-password",
        "login",
        "use-right-token",
        "use-wrong-token",
        // "refresh-token",
        // "use-old-token",
        // "use-new-token",
        // "revoke-token",
        // "use-revoked-tokens"
        // "token-expiration" ???
    ])
    fun login(state: String) = runBlocking {
        testApplication() {
            environment {
                // Load the HOCON file explicitly with the file path
                val configFile = File("src/test/resources/authentication.test.conf")
                config = HoconApplicationConfig(ConfigFactory.parseFile(configFile))
            }
            application {

                // authenticationTest(authenticationRoutingMigrations)
                routing {
                    authenticate("auth-jwt") {
                        get("test") {
                            call.respond(HttpStatusCode.OK, Result.Success("OK"))
                        }
                    }
                }
            }
            suspend fun testCall(accessToken: String? = null) = client.get("test") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                if (accessToken != null) {
                    header(HttpHeaders.Authorization, "Bearer $accessToken")
                }
            }
            val rightUsername = "developer@alpha-structure.com"
            val rightPassword = "pass1234"

            suspend fun login(username: String, password: String) = client.post("/login") {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                setBody(
                    Json.encodeToString(
                        Login.serializer(),
                        Login(
                            username = username,
                            password = password
                        )
                    )
                )
            }
            if (state == "before-login") {
                val beforeLoginResponse = testCall()
                assertFalse("Status is OK") { beforeLoginResponse.status == HttpStatusCode.OK }
                assertTrue("Status is not Unauthorized") { beforeLoginResponse.status == HttpStatusCode.Unauthorized }
            }
            else if(state == "login-with-wrong-username"){
                val loginResponse = login("unknown-user", rightPassword)
                assertTrue("Status is OK but should not"){loginResponse.status != HttpStatusCode.OK}
                assertTrue("Status in not Unauthorized"){loginResponse.status == HttpStatusCode.Unauthorized}
            }
            else if(state == "login-with-wrong-password"){
                val loginResponse = login(rightUsername, "wrong-password")
                assertTrue("Status is OK but should not"){loginResponse.status != HttpStatusCode.OK}
                assertTrue("Status in not Unauthorized"){loginResponse.status == HttpStatusCode.Unauthorized}
            }
            else {

                val loginResponse = login(rightUsername, rightPassword)
                if(state == "login") {
                    val x = loginResponse.bodyAsText()
                    assertTrue("Status not OK") { loginResponse.status == HttpStatusCode.OK }
                    assertTrue("Message has empty body") { x.isNotEmpty() }
                }
                // User is logged in and has a tokens :-)
                val loggedInResult = Json.decodeFromString(ResultSerializer<LoggedIn>(), loginResponse.bodyAsText())
                assertIs<Result.Success<LoggedIn>>(loggedInResult)

                // Check access with and without token
                val accessToken = loggedInResult.data.accessToken
                val refreshToken = loggedInResult.data.refreshToken
                if(state == "use-wrong-token") {
                    val negativeResponse = testCall("1$accessToken")
                    assertFalse("Status is OK") { negativeResponse.status == HttpStatusCode.OK }
                    assertTrue("Status is not Unauthorized") { negativeResponse.status == HttpStatusCode.Unauthorized }
                }
                if(state == "use-right-token") {
                    val positiveResponse = testCall(accessToken)
                    assertTrue("Status not OK") { positiveResponse.status == HttpStatusCode.OK }
                }
                // Refresh token and check access with new token and old token
                if(state == "refresh-token") {
                    kotlin.test.fail("Not implemented yet!")
                }
                // new token  -> expect success
                if(state == "use-new-token") {
                    kotlin.test.fail("Not implemented yet!")
                }
                // old token -> expect fail
                if(state == "use-old-token") {
                    kotlin.test.fail("Not implemented yet!")
                }

                // Revoke token and check access. Expect no token work anymore
                if(state == "revoke-token") {
                    kotlin.test.fail("Not implemented yet!")
                }

                if(state == "use-revoked-tokens") {
                    kotlin.test.fail("Not implemented yet!")
                }

                // Checks with expired tokens
            }
        }
    }
}