package org.solyton.solawi.bid.application.pipeline

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import javax.crypto.SecretKey
import org.solyton.solawi.bid.application.environment.JWT as JWTDATA

fun Application.installAuthentication(jwt: JWTDATA)  {
        install(Authentication) {
            jwt("auth-jwt") {
                val signingKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)//jwt.secret.toByteArray())//
                val algorithm = Algorithm.HMAC256(signingKey.encoded)
                verifier(
                    JWT.require(algorithm)
                        .withIssuer(jwt.domain)
                        .withAudience(jwt.audience)
                        .build()
                )
                validate { credential ->
                     if (credential.payload.audience.contains(jwt.audience)) JWTPrincipal(credential.payload) else null
                }
                challenge { _, _ ->
                    call.respond(HttpStatusCode.Unauthorized, "Token is not valid or expired")
                }
            }
        }
    }
