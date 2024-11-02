package org.solyton.solawi.bid.module.authentication.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.evoleq.math.state.times
import org.evoleq.util.Receive
import org.evoleq.util.Respond
import org.solyton.solawi.bid.application.environment.Environment
// import org.solyton.solawi.bid.application.plugin.AuthenticationHolder.Companion.jwtPrincipal
import org.solyton.solawi.bid.module.authentication.action.Login
import org.solyton.solawi.bid.module.authentication.action.Refresh
import org.solyton.solawi.bid.module.authentication.action.revokeRefreshToken
import org.solyton.solawi.bid.module.authentication.data.api.Login
import org.solyton.solawi.bid.module.authentication.data.api.RefreshToken

fun Routing.authentication(environment: Environment) {
    // Login endpoint
    post("/login") {
        Receive<Login>() * Login(jwt = environment.jwt) * Respond()

    }

    // Refresh token endpoint
    post("/refresh") {
        Receive<RefreshToken>() * Refresh(jwt = environment.jwt) * Respond()
    }

    // Logout endpoint
    post("/logout") {
        val refreshToken = call.receive<Map<String, String>>()["refresh_token"]
        if (refreshToken != null) {
            revokeRefreshToken(refreshToken)
            call.respond(HttpStatusCode.OK, "Logged out successfully")
        } else {
            call.respond(HttpStatusCode.BadRequest, "Refresh token is required for logout")
        }
    }
/*
    // Secure endpoint example
    authenticate("auth-jwt") {
        get("/secure") {
            val principal = call.jwtPrincipal()
            val userId = principal?.payload?.subject ?: "Unknown"
            call.respond(HttpStatusCode.OK, "Hello, $userId!")
        }
    }

 */
}
