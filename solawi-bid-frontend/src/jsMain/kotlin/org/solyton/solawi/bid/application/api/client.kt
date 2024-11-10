package org.solyton.solawi.bid.application.api

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import org.evoleq.ktorx.result.Serializer
import org.evoleq.ktorx.result.Result
import org.evoleq.math.MathDsl
import org.solyton.solawi.bid.application.data.Application

@MathDsl
fun Application.client(loggedIn: Boolean = true) = HttpClient(Js) {
    headers {
        append("ContentType","Application/Json")
        //append("Context", )
        if(loggedIn) {
            append("Authorization", "Bearer ${userData.accessToken}")
        }
    }
}

inline fun <reified S: Any, reified T: Any> HttpClient.get(url: String): suspend (S)-> Result<T> = {s:S ->
    with(post(url){
        if(S::class != Unit::class) {
            val serialized = Json.encodeToString(Serializer(), s)
            setBody(serialized)
        }
    }){
        Json.decodeFromString<Result<T>>(Serializer(), bodyAsText())
    }
}

inline fun <reified S: Any, reified T: Any> HttpClient.post(url: String): suspend (S)-> Result<T> = {s:S ->
    with(post(url){
        if(S::class != Unit::class) {
            val serialized = Json.encodeToString(Serializer(), s)
            setBody(serialized)
        }
    }){
        Json.decodeFromString<Result<T>>(Serializer(), bodyAsText())
    }
}