package org.solyton.solawi.bid.module.i18n.api

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import org.solyton.solawi.bid.application.data.env.Environment

suspend fun Environment.i18n(locale: String): String =
    with(HttpClient(Js)) {
        get("$frontendUrl/i18n/$locale"){
            port = frontendPort
        }.bodyAsText()
    }


suspend fun Environment.i18n(): Any =
    with(HttpClient(Js)) {
        get("$frontendUrl/i18n/"){
            port = frontendPort
        }.bodyAsText()
    }
