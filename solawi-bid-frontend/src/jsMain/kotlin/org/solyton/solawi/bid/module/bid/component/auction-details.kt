package org.solyton.solawi.bid.module.bid.component

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.compose.layout.PropertiesStyles
import org.evoleq.compose.layout.Property
import org.evoleq.compose.layout.ReadOnlyProperties
import org.evoleq.optics.storage.Storage
import org.solyton.solawi.bid.module.bid.data.Auction

@Markup
@Composable
@Suppress("FunctionName")
fun AuctionDetails(auction: Storage<Auction>, styles: PropertiesStyles = PropertiesStyles()) {
    // todo:i18n
    val details = auction.read().auctionDetails
    ReadOnlyProperties(
        listOf(
            Property("Target Amount", "${details.targetAmount?:"-"}"),
            Property("Benchmark", "${details.benchmark?:"-"}"),
            Property("Minimal Bid", "${details.minimalBid?:"-"}"),
            Property("SolidarityContribution", "${details.solidarityContribution?:"-"}")
        ),
        styles
    )
}
