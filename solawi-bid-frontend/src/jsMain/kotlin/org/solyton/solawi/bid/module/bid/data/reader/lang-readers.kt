package org.solyton.solawi.bid.module.bid.data.reader

import org.evoleq.language.Lang
import org.evoleq.language.component
import org.evoleq.language.get
import org.evoleq.math.Reader

sealed class Component(val path: String) {
    data object AuctionsPage : Component("solyton.auction.auctionsPage")
    data object AuctionPage : Component("solyton.auction.auctionPage")
}

val component: (Component)->Reader<Lang, Lang.Block> = {c -> Reader { lang -> (lang as Lang.Block).component(c.path) }}
val subComp: (String)->Reader<Lang, Lang.Block> = {c -> Reader { lang -> (lang as Lang.Block).component(c) }}

val title: Reader<Lang.Block, String> = Reader { lang -> lang["title"] }
val targetAmount: Reader<Lang.Block, String> = Reader { lang -> lang["targetAmount"] }
val benchmark: Reader<Lang.Block, String> = Reader { lang -> lang["benchmark"] }
val date: Reader<Lang.Block, String> = Reader { lang -> lang["date"] }
val solidarityContribution: Reader<Lang.Block, String> = Reader { lang -> lang["solidarityContribution"] }
val minimalBid: Reader<Lang.Block, String> = Reader { lang -> lang["minimalBid"] }
val numberOfShares: Reader<Lang.Block, String> = Reader { lang -> lang["numberOfShares"] }
val numberOfBidders: Reader<Lang.Block, String> = Reader { lang -> lang["numberOfBidders"] }

val text: Reader<Lang.Block, String> = Reader { lang -> lang["text"] }
