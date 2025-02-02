package org.solyton.solawi.bid.module.bid.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.evoleq.ktorx.result.Result
import org.evoleq.math.state.runOn
import org.evoleq.math.state.times
import org.evoleq.util.Base
import org.evoleq.util.Fail
import org.evoleq.util.Receive
import org.evoleq.util.Respond
import org.solyton.solawi.bid.application.environment.Environment
import org.solyton.solawi.bid.module.bid.action.db.*
import org.solyton.solawi.bid.module.bid.data.api.*
import java.util.*


@KtorDsl
fun Routing.bid(environment: Environment,authenticate: Routing.(Route.() -> Route)-> Route) =
    authenticate {
        route("bid") {

            get("all") {
                call.respond(Result.Success("not impl yet!"))
            }

            post("send") {
                (Receive<Bid>() * StoreBid * Respond()) runOn Base(call, environment)
            }
        }
    }
@KtorDsl
fun Routing.auction(environment: Environment,authenticate: Routing.(Route.() -> Route)-> Route) =
    authenticate{
        route("auction"){
            post("create") {
                (Receive<CreateAuction>() * CreateAuction * Respond<Auction>()) runOn Base(call, environment)
            }
            patch("update") {
                (Receive<UpdateAuctions>() * UpdateAuctions * ReadAuctions * Respond<Auctions>()) runOn Base(call, environment)
            }
            patch("configure") {
                Receive<ConfigureAuction>() * ConfigureAuction * Respond<Auction>() runOn Base(call, environment)
            }
            delete("delete") {
                (Receive<DeleteAuctions>() * DeleteAuctions * ReadAuctions * Respond<Auctions>()) runOn Base(call, environment)
            }

            get("results") {

            }
            get("all"){
                (Receive(GetAuctions) * ReadAuctions * Respond<Auctions>()) runOn Base(call, environment)
            }
            route("bidder") {
                post("import") {
                    (Receive<ImportBidders>() *
                    ImportBidders *
                    Respond<Auction>()) runOn Base(call, environment)
                }
                delete("delete"){
                    // will delete all listed bidders
                    (Receive<DeleteBidders>() * Fail("Not Implemented") * Respond()) runOn Base(call, environment)
                }
            }

        }
    }

@KtorDsl
fun Routing.round(environment: Environment,authenticate: Routing.(Route.() -> Route)-> Route) =
    authenticate{
        route("round") {
            post("create") {
                Receive<CreateRound>() * AddRound * Respond() runOn Base(call,environment)
            }
            patch("change-state") {
                Receive<ChangeRoundState>() * ChangeRoundState *  Respond() runOn Base(call,environment)
            }
            route("{roundId}") {
                get("export-results") {
                    val roundId = UUID.fromString(call.parameters["id"]!!)
                    Receive(roundId) * ExportResults * Respond() runOn Base(call, environment)
                }
            }
        }
    }
