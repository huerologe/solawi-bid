package org.evoleq.ktorx.client

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import org.evoleq.ktorx.api.Api
import org.evoleq.ktorx.result.Result
import org.evoleq.ktorx.result.ResultSerializer
import org.jetbrains.compose.web.testutils.ComposeWebExperimentalTestsApi
import org.jetbrains.compose.web.testutils.runTest
import org.solyton.solawi.bid.api.solawiApi
import org.solyton.solawi.bid.application.api.post
import org.solyton.solawi.bid.application.serialization.installSerializers
import org.solyton.solawi.bid.module.authentication.data.api.LoggedIn
import org.solyton.solawi.bid.module.authentication.data.api.Login
import org.solyton.solawi.bid.module.authentication.data.api.Logout
import org.solyton.solawi.bid.module.authentication.data.api.RefreshToken
import kotlin.test.Test
import kotlin.test.assertIs

class HttpRequestTests {



    @OptIn(ComposeWebExperimentalTestsApi::class)
    @Test
    fun check() = runTest{
        installSerializers()
        val mockEngine = solawiApi.toMockEngine()
        val client = HttpClient(mockEngine)

        val result = client.post<Login, LoggedIn>("login", 9876) (Login("username", "password"))
        assertIs<Result.Success<LoggedIn>>(result)
    }

}

val sampleResponses = mapOf(
    Login::class to LoggedIn("session", "access-token", "refresh-token"),
    RefreshToken::class to LoggedIn("session", "access-token", "refresh-token"),
    Logout::class to Unit
)

fun Api.toMockEngine(): MockEngine = MockEngine{
    request -> with(this@toMockEngine.entries.find { "/${it.value.url}" == request.url.fullPath }!!.key) {
        respond(
            content = Json.encodeToString(ResultSerializer, Result.Success(sampleResponses[this]!!))
        )
    }
}