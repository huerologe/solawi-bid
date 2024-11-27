package org.solyton.solawi.bid.module.bid.component

import androidx.compose.runtime.Composable
import io.ktor.http.*
import org.evoleq.compose.Markup
import org.evoleq.optics.lens.FirstBy
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.module.bid.data.Auction
import org.solyton.solawi.bid.module.bid.data.date
import org.solyton.solawi.bid.module.bid.data.name

@Markup
@Composable
@Suppress("FunctionName")
fun AuctionList(auctions: Storage<List<Auction>>, styles: AuctionListStyles = AuctionListStyles()) = Div(
    attrs = {style{styles.wrapper(this)}}
) {
    with(auctions.read()) {
        forEach{ auction ->
            AuctionListItem(auctions * FirstBy<Auction> { it.id == auction.id}, styles)
        }
    }
}

@Markup
@Composable
@Suppress("FunctionName")
fun AuctionListItem(auction: Storage<Auction>, styles: AuctionListStyles = AuctionListStyles()) = Div(attrs = {
    style { styles.item(this) }
}) {
    // date
    Div() {
        val date = (auction * date).read()
        Text(date.toDateString())
    }
    // name
    Div() {
        val name = (auction * name).read()
        Text(name)
    }

    Div() {
        Text("Actions")
    }
}

data class AuctionListStyles (
    val wrapper: StyleScope.()->Unit = {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        width(100.percent)
        height(100.percent)

    },
    val item: StyleScope.()->Unit = {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        width(100.percent)
    }
)
