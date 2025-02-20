package org.solyton.solawi.bid.application.routing

import androidx.compose.runtime.Composable
import org.evoleq.compose.routing.*
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.navbar.navBar
import org.solyton.solawi.bid.application.data.userData
import org.solyton.solawi.bid.application.service.isLoggerIn
import org.solyton.solawi.bid.application.ui.page.auction.AuctionPage
import org.solyton.solawi.bid.application.ui.page.auction.AuctionsPage
import org.solyton.solawi.bid.application.ui.page.auction.BidRoundEvaluationPage
import org.solyton.solawi.bid.application.ui.page.auction.RoundPage
import org.solyton.solawi.bid.application.ui.page.dashboard.DashboardPage
import org.solyton.solawi.bid.application.ui.page.login.LoginPage
import org.solyton.solawi.bid.application.ui.page.sendbid.SendBidPage
import org.solyton.solawi.bid.application.ui.page.test.MobileTestPage
import org.solyton.solawi.bid.application.ui.page.test.TestPage
import org.solyton.solawi.bid.module.navbar.component.NavBar

@RoutingDsl
@Composable
@Suppress("FunctionName")
fun Routing(storage: Storage<Application>): Routes = Routing("/") {
    route("bid/send/:cryptoId") {
        component {
            SendBidPage(storage, "${parameter("cryptoId")}")
        }
    }
    route("login") {
        component { LoginPage(storage) }
    }
    route("solyton") {
        wrap {
            access{
                // todo:dev far too simple!
                when((storage * userData).read().isLoggerIn()) {
                    true -> true
                    false -> when{
                        currentPath().startsWith("/bid/send") -> true
                        else ->{
                            navigate("/login")
                            false
                        }
                    }
                }
            }
            layout {
                {
                    NavBar(storage * navBar)
                    // render page
                    it()
                }
            }

            route("dashboard") {
                component { DashboardPage(storage) }
            }

            route("manage") {

            }

            route("auctions") {
                component {
                    AuctionsPage(storage)
                }
                route(":auctionId") {
                    component {
                        AuctionPage(storage, parameter("auctionId")!!)
                    }
                    route("rounds/:roundId") {
                        component{
                            val auctionId = parameter("auctionId")!!
                            val roundId = parameter("roundId")!!

                            RoundPage(
                                storage,
                                auctionId,
                                roundId
                                //storage * auctions * FirstBy { it.auctionId == auctionId } * rounds * FirstBy { it.roundId == roundId }
                            )
                        }

                        route("evaluation"){
                            component {
                                BidRoundEvaluationPage(
                                    storage,
                                    parameter("auctionId")!!,
                                )
                            }
                        }
                    }
                }
            }



            route("logout") {

                component{Text("Logged out")}
            }

            route("test") {
                component { TestPage() }
            }
            route("test-mobile") {
                component { MobileTestPage(storage) }
            }
        }
    }
}
