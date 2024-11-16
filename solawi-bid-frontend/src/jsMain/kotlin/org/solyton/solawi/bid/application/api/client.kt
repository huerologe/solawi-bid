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

inline fun <reified S: Any, reified T: Any> HttpClient.get(url: String, port: Int): suspend (S)-> Result<T> = {s:S ->
    with(get(url){
        this.port = port
        if(S::class != Unit::class) {
            val serialized = Json.encodeToString(Serializer(), s)
            setBody(serialized)
        }
    }){
        Json.decodeFromString<Result<T>>(Serializer(), bodyAsText())
    }
}

inline fun <reified S: Any, reified T: Any> HttpClient.post(url: String, port: Int): suspend (S)-> Result<T> = {s:S ->
    console.log("input = $s", "url = $url", "port = $port")

    with(post(url){
        this.port = port
        if(S::class != Unit::class) {
            val serialized = Json.encodeToString(Serializer(), s)
            setBody(serialized)

        }
    }){
        Json.decodeFromString<Result<T>>(Serializer(), bodyAsText())
    }
}