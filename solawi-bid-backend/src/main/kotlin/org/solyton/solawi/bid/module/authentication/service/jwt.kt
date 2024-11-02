package org.solyton.solawi.bid.module.authentication.service


import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.solyton.solawi.bid.application.environment.JWT
import java.util.*


private const val ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15 // 15 minutes

// Generate access token
fun generateAccessToken(userId: String, jwt: JWT): String {
    return Jwts.builder()
        .setSubject(userId)
        .setIssuer(jwt.domain)
        .setAudience(jwt.audience)
        .setExpiration(Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
        .signWith(SignatureAlgorithm.HS256, jwt.secret)
        .compact()
}

fun generateRefreshToken() = UUID.randomUUID().toString()