package org.solyton.solawi.bid.module.user.routing

import org.solyton.solawi.bid.application.environment.Environment
import org.solyton.solawi.bid.module.user.action.getAllUsers
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.evoleq.math.state.runOn
import org.evoleq.util.Base
//import org.solyton.solawi.bid.application.plugin.AuthenticationHolder.Companion.jwtPrincipal

@KtorDsl
fun Routing.user(environment: Environment){//, authenticateMe: Routing.(Route.() -> Route)-> Route) {
    //authenticate("auth-jwt") {
        get("users/all") {

            // val principal = call.authentication.principal<JWTPrincipal>()
           // val userId = principal?.payload?.subject ?: "Unknown"
            getAllUsers() runOn Base(call, environment)
        }
    //}
}
