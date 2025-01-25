package org.solyton.solawi.bid.application.pipeline

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json
import org.evoleq.ktorx.result.Result
import org.evoleq.ktorx.result.ResultSerializer
import org.solyton.solawi.bid.application.exception.ApplicationException

fun Application.interceptAndValidateHeaders() {
    intercept(ApplicationCallPipeline.Plugins) {
        // validate that the CONTEXT Header is present
        val contextHeader = call.request.headers["CONTEXT"]
        if (contextHeader.isNullOrEmpty()) {
            call.response.headers.append("CONTEXT", "EMPTY")
            call.respondText(
                Json.encodeToString(
                    ResultSerializer(), Result.Failure.Message(ApplicationException.MissingContextHeader.message) as Result<String>),
                status = HttpStatusCode.BadRequest
            )
            finish()
        }
        else {
            call.response.headers.append("CONTEXT", contextHeader)
        }
    }
}