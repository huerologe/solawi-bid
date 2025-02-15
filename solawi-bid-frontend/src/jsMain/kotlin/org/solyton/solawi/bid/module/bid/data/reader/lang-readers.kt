package org.solyton.solawi.bid.module.bid.data.reader

import org.evoleq.language.Lang
import org.evoleq.language.LangComponent
import org.evoleq.language.get
import org.evoleq.math.Reader

sealed class BidComponent(override val path: String): LangComponent {
    data object AuctionsPage : BidComponent("solyton.auction.auctionsPage")
    data object AuctionPage : BidComponent("solyton.auction.auctionPage")
    data object Round : BidComponent("solyton.auction.round")
}

val targetAmount: Reader<Lang.Block, String> = Reader { lang -> lang["targetAmount"] }
val benchmark: Reader<Lang.Block, String> = Reader { lang -> lang["benchmark"] }
val solidarityContribution: Reader<Lang.Block, String> = Reader { lang -> lang["solidarityContribution"] }
val minimalBid: Reader<Lang.Block, String> = Reader { lang -> lang["minimalBid"] }
val numberOfShares: Reader<Lang.Block, String> = Reader { lang -> lang["numberOfShares"] }
val numberOfBidders: Reader<Lang.Block, String> = Reader { lang -> lang["numberOfBidders"] }
val rounds: Reader<Lang.Block, String> = Reader { lang -> lang["rounds"] }


