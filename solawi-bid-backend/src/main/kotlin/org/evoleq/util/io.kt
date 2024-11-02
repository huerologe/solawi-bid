package org.evoleq.util

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.*
import org.evoleq.exposedx.NoMessageProvided
import org.evoleq.math.x
import org.evoleq.ktorx.result.Result
// import org.solyton.solawi.bid.application.plugin.AuthenticationHolder.Companion.jwtPrincipal
import org.solyton.solawi.bid.module.authentication.exception.AuthenticationException
import org.solyton.solawi.bid.module.db.BidRoundException


fun <T : Any> Authenticate(): Action<JWTPrincipal?> = ApiAction {
    call -> call.authentication.principal<JWTPrincipal>() x call
}



@KtorDsl
@Suppress("FunctionName")
suspend inline fun <reified T : Any>  AuthReceive(): KlAction<JWTPrincipal? ,Result<T>> = {principal -> ApiAction {
     call -> if(principal != null) {
        Result.Success(call.receive<T>())
     } else {

        Result.Failure.Exception(AuthenticationException.InvalidOrExpiredToken)
} x call

}}

@KtorDsl
@Suppress("FunctionName")
suspend inline fun <reified T : Any>  Receive(): Action<Result<T>> = ApiAction {
    call-> try{
        Result.Success(call.receive<T>())
    } catch (e: Exception) {
        Result.Failure.Exception(e)
    } x call
}

@KtorDsl
@Suppress("FunctionName")
suspend inline fun <reified T : Any>  Respond(): KlAction<Result<T>, Unit> = {result -> ApiAction {
        call-> when(result){
            is Result.Success<*> ->  call.respond(HttpStatusCode.OK, result)
            is Result.Failure.Message ->  call.respond(HttpStatusCode.InternalServerError, result)
            is Result.Failure.Exception -> with(result.transform()){
                call.respond(first, second)
            }
        } x call
} }

fun Result.Failure.Exception.transform(): Pair<HttpStatusCode, Result.Failure.Message> =
    when(this.value) {
        is AuthenticationException.InvalidOrExpiredToken -> HttpStatusCode.Unauthorized

        is BidRoundException.RoundNotStarted -> HttpStatusCode.Conflict
        is BidRoundException.NoSuchRound,
        is BidRoundException.NoSuchRoundState,
        is BidRoundException.NoSuchAuction -> HttpStatusCode.NotFound
        is BidRoundException.UnregisteredBidder,
        is BidRoundException.RegisteredBidderNotPartOfTheAuction,
        is BidRoundException.LinkNotPresent, -> HttpStatusCode.Forbidden
        is BidRoundException.IllegalNumberOfParts -> HttpStatusCode.BadRequest
        //
        else -> HttpStatusCode.InternalServerError
    } x this.value.toMessage()

fun Throwable.toMessage(): Result.Failure.Message = Result.Failure.Message(message?: NoMessageProvided)