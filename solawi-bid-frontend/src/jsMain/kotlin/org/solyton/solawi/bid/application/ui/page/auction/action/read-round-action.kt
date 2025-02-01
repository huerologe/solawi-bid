package org.solyton.solawi.bid.application.ui.page.auction.action

import org.evoleq.compose.Markup
import org.evoleq.math.Reader
import org.evoleq.math.contraMap
import org.evoleq.optics.lens.FirstBy
import org.evoleq.optics.lens.times
import org.evoleq.optics.storage.Action
import org.evoleq.optics.transform.times
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.auctions
import org.solyton.solawi.bid.application.data.userData
import org.solyton.solawi.bid.module.bid.data.api.ApiRound
import org.solyton.solawi.bid.module.bid.data.api.GetRound
import org.solyton.solawi.bid.module.bid.data.rounds
import org.solyton.solawi.bid.module.bid.data.toDomainType
import org.solyton.solawi.bid.module.user.User

/*
@Markup
fun readRound(auctionId: String, roundId: String): Action<Application, GetRound, ApiRound> = Action(
    name ="ReadRound",
    reader = userData * Reader { user: User -> GetRound(roundId) },
    endPoint = GetRound::class,
    writer = (
        auctions * FirstBy { it.auctionId == auctionId } *
        rounds * FirstBy { it.roundId == roundId }
    ).set contraMap {
        a: ApiRound -> a.toDomainType()
    }
)

 */