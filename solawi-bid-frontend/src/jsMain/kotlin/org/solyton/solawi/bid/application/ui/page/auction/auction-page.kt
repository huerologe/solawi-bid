package org.solyton.solawi.bid.application.ui.page.auction

import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.evoleq.compose.Markup
import org.evoleq.compose.routing.navigate
import org.evoleq.language.Lang
import org.evoleq.language.component
import org.evoleq.optics.lens.FirstBy
import org.evoleq.optics.lens.times
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.dom.*
import org.solyton.solawi.bid.application.data.*
import org.solyton.solawi.bid.application.ui.page.auction.action.changeRoundState
import org.solyton.solawi.bid.application.ui.page.auction.action.createRound
import org.solyton.solawi.bid.application.ui.page.auction.action.importBidders
import org.solyton.solawi.bid.application.ui.page.auction.action.readAuctions
import org.solyton.solawi.bid.module.bid.component.showImportBiddersModal
import org.solyton.solawi.bid.module.bid.data.api.NewBidder
import org.solyton.solawi.bid.module.bid.data.api.RoundState
import org.solyton.solawi.bid.module.bid.data.api.nextState
import org.solyton.solawi.bid.module.bid.data.rounds
import org.solyton.solawi.bid.module.error.component.showErrorModal
import org.solyton.solawi.bid.module.error.lang.errorModalTexts
import org.solyton.solawi.bid.module.i18n.data.language
import org.solyton.solawi.bid.module.qrcode.QRCodeSvg

@Markup
@Composable
@Suppress("FunctionName")
fun AuctionPage(storage: Storage<Application>, auctionId: String) = Div{

    var newBidders by remember { mutableStateOf<List<NewBidder>>(listOf()) }

    LaunchedEffect(Unit) {
        (storage * actions).read().emit(readAuctions())
    }

    val auction = auctions * FirstBy{ it.auctionId == auctionId }

    H1 { Text( (storage * auction).read().name ) }

    // Show details:
    // - Date
    // - Benchmark
    // - Solidarity ...
    // - Target Amount

    Button(attrs = {
        onClick {
            (storage * modals).showImportBiddersModal(
                storage * auction,
                texts = ((storage * i18N * language).read() as Lang.Block).component("solyton.auction.importBiddersDialog"),
                setBidders = {newBidders = it},
                cancel = {},
                update = {
                    CoroutineScope(Job()).launch {
                        (storage * actions).read().emit(importBidders(newBidders, auction))
                    }
                }
            )
        }
    }) { Text("Import Bidders") }



    H2 { Text("Rounds") }
    Button(attrs = {
        onClick {
            CoroutineScope(Job()).launch {
                val actions = (storage * actions).read()
                try {
                    actions.emit( createRound(auction) )
                } catch(exception: Exception) {
                    (storage * modals).showErrorModal(
                        errorModalTexts(exception.message?:exception.cause?.message?:"Cannot Emit action 'CreateRound'")
                    )
                }
            }
        }
    }) { Text("Create new Round") }




    // Show list of rounds ordered by date - descending

    // Each list item shall contain
    // a crypto link,
    // the state of the round
    // a button "next state" (start, stop, evaluate, ...)
    // a link to the evaluation page
    // a link to the details of the round
    val frontendBaseUrl = with((storage * environment).read()){
        "$frontendUrl:$frontendPort"
    }
    (storage * auction * rounds).read().forEach { round ->
        Div(attrs = {
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
            }
        }) {
            Button(
                attrs = {
                    onClick {
                        navigate("solyton/auctions/${auctionId}/rounds/${round.roundId}")
                    }
                }
            ){
                QRCodeSvg("$frontendBaseUrl/bid/send/${round.link}")
            }
            Div {
                Text(round.state)
            }
            Button(attrs = {
                onClick {
                    CoroutineScope(Job()).launch {
                        val actions = (storage * actions).read()
                        try {
                            actions.emit( changeRoundState(
                                RoundState.fromString(round.state).nextState(),
                                auction * rounds * FirstBy { it.roundId == round.roundId })
                            )
                        } catch(exception: Exception) {
                            (storage * modals).showErrorModal(
                                errorModalTexts(exception.message?:exception.cause?.message?:"Cannot Emit action 'CreateRound'")
                            )
                        }
                    }
                }
            }
            ) {
                Text(RoundState.fromString(round.state).commandName)
            }
        }
    }
}
