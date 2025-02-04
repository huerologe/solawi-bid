package org.solyton.solawi.bid.application.ui.page.auction

import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.evoleq.compose.Markup
import org.evoleq.compose.layout.*
import org.evoleq.compose.routing.navigate
import org.evoleq.language.Lang
import org.evoleq.language.component
import org.evoleq.math.Reader
import org.evoleq.math.emit
import org.evoleq.math.map
import org.evoleq.optics.lens.FirstBy
import org.evoleq.optics.lens.times
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.solyton.solawi.bid.application.data.*
import org.solyton.solawi.bid.application.ui.page.auction.action.*
import org.solyton.solawi.bid.module.bid.component.AuctionDetails
import org.solyton.solawi.bid.module.bid.component.downloadCsv
import org.solyton.solawi.bid.module.bid.component.showImportBiddersModal
import org.solyton.solawi.bid.module.bid.component.showUpdateAuctionModal
import org.solyton.solawi.bid.module.bid.data.*
import org.solyton.solawi.bid.module.bid.data.api.NewBidder
import org.solyton.solawi.bid.module.bid.data.api.RoundState
import org.solyton.solawi.bid.module.bid.data.api.nextState
import org.solyton.solawi.bid.module.bid.service.toCsvContent
import org.solyton.solawi.bid.module.error.component.showErrorModal
import org.solyton.solawi.bid.module.error.lang.errorModalTexts
import org.solyton.solawi.bid.module.i18n.data.language
import org.solyton.solawi.bid.module.qrcode.QRCodeSvg
import org.solyton.solawi.bid.module.separator.LineSeparator
import kotlin.js.Date

val auctionPropertiesStyles = PropertiesStyles(
    containerStyle = { width(40.percent) },
    propertyStyles = PropertyStyles(
        keyStyle = { width(50.percent) },
        valueStyle = { width(50.percent) }
    )
)

@Markup
@Composable
@Suppress("FunctionName")
fun AuctionPage(storage: Storage<Application>, auctionId: String) = Div{

    var newBidders by remember { mutableStateOf<List<NewBidder>>(listOf()) }

    LaunchedEffect(Unit) {
        (storage * actions).read().emit(readAuctions())
    }

    val auction = auctions * FirstBy{ it.auctionId == auctionId }

    H1 { Text( with((storage * auction).read()) { name }  ) }
    LineSeparator()
    Horizontal(styles = { justifyContent(JustifyContent.SpaceBetween); width(100.percent) }) {
        H2 { Text("Details") }
        Horizontal {
            Button( attrs = {
                // Auction can only be configured, if no rounds have been created
                val isDisabled = (storage * auction * rounds * existRounds).emit()
                if(isDisabled) attr("disabled", "true")
                style{

                    // todo:style:button:edit
                }
                onClick {
                    // open configuration dialog
                    if(isDisabled) return@onClick
                    (storage * modals).showUpdateAuctionModal(
                        auction =  storage * auction,
                        texts = ((storage * i18N * language).read() as Lang.Block).component("solyton.auction.updateDialog"),
                        cancel = {}
                    ) {
                        CoroutineScope(Job()).launch {
                            val action = configureAuction(auction)
                            val actions = (storage * actions).read()
                            try {
                                actions.emit( configureAuction(auction) )
                            } catch(exception: Exception) {
                                (storage * modals).showErrorModal(
                                    errorModalTexts(exception.message?:exception.cause?.message?:"Cannot Emit action '${action.name}'")
                                )
                            }
                        }
                    }
                }
            } ){
                Text("Configure")
            }
            Button(attrs = {
                // Bidders can only be imported, if no rounds have been created
                val isDisabled = (storage * auction * rounds * existRounds).emit()
                if(isDisabled) attr("disabled", "true")
                onClick {
                    if(isDisabled) return@onClick
                    (storage * modals).showImportBiddersModal(
                        storage * auction,
                        texts = ((storage * i18N * language).read() as Lang.Block).component("solyton.auction.importBiddersDialog"),
                        setBidders = { newBidders = it },
                        cancel = {},
                        update = {
                            CoroutineScope(Job()).launch {
                                (storage * actions).read().emit(importBidders(newBidders, auction))
                            }
                        }
                    )
                }
            }) { Text("Import Bidders") }
            // New rounds can only be created when
            // 1. the auction is configured,
            // 2. the bidders have been imported and
            // 3. There are no open or running rounds
            val isDisabled = (storage * auction * rounds * existsRunning).emit() ||
                (storage * auction * auctionDetails * areNotConfigured).emit() ||
                (storage * auction * biddersHaveNotBeenImported).emit()
            Button(attrs = {
                if(isDisabled) attr("disabled", "true")
                onClick {
                    if(isDisabled) return@onClick
                    CoroutineScope(Job()).launch {
                        val actions = (storage * actions).read()
                        try {
                            actions.emit(createRound(auction))
                        } catch (exception: Exception) {
                            (storage * modals).showErrorModal(
                                errorModalTexts(
                                    exception.message ?: exception.cause?.message ?: "Cannot Emit action 'CreateRound' in update mode"
                                )
                            )
                        }
                    }
                }
            }) { Text("Create new Round") }
        }

    }
    Horizontal {
        AuctionDetails(
            storage * auction,
            auctionPropertiesStyles
        )
        ReadOnlyProperties(
            listOf(
                Property("Date", with((storage * auction).read()) { date }),
                Property("Number of Bidders", (storage * auction * countBidders).emit()),
                //Property("Number of Shares", (storage * auction * countBidders).emit())
            ),
            auctionPropertiesStyles
        )
    }

    LineSeparator()

    H2 { Text("Rounds") }





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
    (storage * auction * rounds).read().reversed().forEach { round ->
        Div(attrs = {
            style {
                display(DisplayStyle.Flex)
                width(100.percent)
            }
        }) {
            if(round.rawResults.startDownloadOfBidRoundResults) {
                LaunchedEffect(Unit) {
                    val fileName = "results_${Date.now()}.csv"
                    val csvContent = round.rawResults.toCsvContent()
                    downloadCsv(csvContent, fileName)
                }
                val startDownload = (storage * auction * rounds * FirstBy { it.roundId == round.roundId }) * rawResults  * startDownloadOfBidRoundResults
                startDownload.write(false)
            }
            Button(
                attrs = {
                    style {
                        flexShrink(0)
                    }
                    onClick {
                        navigate("/solyton/auctions/${auctionId}/rounds/${round.roundId}")
                    }
                }
            ){
                QRCodeSvg(
                    round.roundId,
                    "$frontendBaseUrl/bid/send/${round.link}",
                    64.0
                )
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
                                errorModalTexts(exception.message?:exception.cause?.message?:"Cannot Emit action 'ChangeRoundState'")
                            )
                        }
                    }
                }
            }
            ) {
                Text(RoundState.fromString(round.state).commandName)
            }

            Button(attrs= {
                onClick {
                    CoroutineScope(Job()).launch {
                        val actions = (storage * actions).read()
                        try {
                            actions.emit( exportBidRoundResults(
                                (storage * auction).read().auctionId,
                                auction * rounds * FirstBy { it.roundId == round.roundId })
                            )
                        } catch(exception: Exception) {
                            (storage * modals).showErrorModal(
                                errorModalTexts(exception.message?:exception.cause?.message?:"Cannot Emit action 'ExportBidRound'")
                            )
                        }
                    }
                }
            }) {
                Text("Export")
            }
        }
    }
}




val countBidders: Reader<Auction, Int> = Reader {
    it.bidderIds.distinct().size
}

val biddersHaveNotBeenImported = countBidders map { it <= 0 }

val countShares: Reader<Auction, Int> = Reader {
    it.bidderIds.distinct().size
}

val existRounds: Reader<List<Round>, Boolean> = Reader {
    rounds -> rounds.isNotEmpty()
}

val existsRunning: Reader<List<Round>, Boolean> = Reader { rounds ->
    val states = rounds.map { it.state }
    val result = states.contains(RoundState.Opened.toString()) || states.contains(RoundState.Started.toString())
    result
}

val areNotConfigured: Reader<AuctionDetails, Boolean> = Reader { details ->
        details.benchmark == null ||
        details.solidarityContribution == null ||
        details.minimalBid == null ||
        details.targetAmount  == null
}

