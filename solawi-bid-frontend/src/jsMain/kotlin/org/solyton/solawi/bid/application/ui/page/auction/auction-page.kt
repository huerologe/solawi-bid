package org.solyton.solawi.bid.application.ui.page.auction

import androidx.compose.runtime.*
import org.evoleq.compose.Markup
import org.evoleq.compose.layout.*
import org.evoleq.math.emit
import org.evoleq.optics.lens.FirstBy
import org.evoleq.optics.lens.times
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.actions
import org.solyton.solawi.bid.application.data.auctions
import org.solyton.solawi.bid.application.ui.page.auction.action.readAuctions
import org.solyton.solawi.bid.module.bid.component.AuctionDetails
import org.solyton.solawi.bid.module.bid.component.BidRoundList
import org.solyton.solawi.bid.module.bid.component.button.CreateNewRoundButton
import org.solyton.solawi.bid.module.bid.component.button.ImportBiddersButton
import org.solyton.solawi.bid.module.bid.component.button.UpdateAuctionButton
import org.solyton.solawi.bid.module.bid.data.api.NewBidder
import org.solyton.solawi.bid.module.bid.data.reader.countBidders
import org.solyton.solawi.bid.module.separator.LineSeparator

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
    // Data
    var newBidders by remember { mutableStateOf<List<NewBidder>>(listOf()) }

    LaunchedEffect(Unit) {
        (storage * actions).read().emit(readAuctions())
    }

    val auction = auctions * FirstBy{ it.auctionId == auctionId }

    // Markup
    H1 { Text( with((storage * auction).read()) { name }  ) }
    LineSeparator()
    Horizontal(styles = { justifyContent(JustifyContent.SpaceBetween); width(100.percent) }) {
        // todo:i18n
        H2 { Text("Details") }
        Horizontal {
            UpdateAuctionButton(
                storage = storage,
                auction = auction
            )
            ImportBiddersButton(
                storage = storage,
                newBidders = Storage<List<NewBidder>>(
                    read = {newBidders},
                    write = {newBidders = it}
                ),
                auction = auction
            )
            CreateNewRoundButton(
                storage = storage,
                auction = auction
            )
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
                // todo:dev
                //Property("Number of Shares", (storage * auction * countBidders).emit())
            ),
            auctionPropertiesStyles
        )
    }

    LineSeparator()

    BidRoundList(
        storage = storage,
        auction = auction
    )
}
