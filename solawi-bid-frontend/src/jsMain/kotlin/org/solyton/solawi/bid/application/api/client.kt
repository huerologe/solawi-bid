package org.solyton.solawi.bid.application.api

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.evoleq.ktorx.result.Result
import org.evoleq.ktorx.result.ResultSerializer
import org.evoleq.ktorx.result.Serializer
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

fun <S: Any,T: Any> HttpClient.post(url: String, port: Int, serializer: KSerializer<S>, deserializer: KSerializer<Result<T>>): suspend (S)-> Result<T> = { s: S ->
    with(post(url) {
        this.port = port
        if (s::class != Unit::class) {
            val serialized = Json.encodeToString(serializer, s)
            setBody(serialized)
        }
    }) {
        Json.decodeFromString<Result<T>>(deserializer, bodyAsText())
    } }

fun <S: Any,T: Any> HttpClient.get(url: String, port: Int, serializer: KSerializer<S>, deserializer: KSerializer<Result<T>>): suspend (S)-> Result<T> = { s: S ->
    with(get(url) {
        this.port = port
        if (s::class != Unit::class) {
            val serialized = Json.encodeToString(serializer, s)
            setBody(serialized)
        }
    }) {
        Json.decodeFromString<Result<T>>(deserializer, bodyAsText())
    } }









inline fun <reified S:Any, reified T: Any> HttpClient.get(url: String, port: Int): suspend (S)-> Result<T> = {s:S ->
    try { withTimeout(1_000) {
        with(get(url){
            this.port = port
            if(s::class != Unit::class) {
                val serialized = Json.encodeToString(Serializer(), s)
                setBody(serialized)
            }
        }){
        Json.decodeFromString<Result<T>>(ResultSerializer(), bodyAsText())
    } }
    } catch (exception: Exception) {
        Result.Failure.Exception(exception)
    }
}

inline fun <reified S: Any, reified T: Any> HttpClient.post(url: String, port: Int): suspend (S)-> Result<T> = {s:S ->
    try { withTimeout(1_000) {
        with(post(url) {
            this.port = port
            if (S::class != Unit::class) {
                val serialized = Json.encodeToString(Serializer(), s)
                setBody(serialized)
            }
        }) {
            Json.decodeFromString<Result<T>>(ResultSerializer(), bodyAsText())
        } }
    } catch (exception: Exception) {
        Result.Failure.Exception(Exception("hey: ${exception.message?: "No message provided"}"))
    }
}

