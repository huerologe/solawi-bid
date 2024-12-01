package org.solyton.solawi.bid.application.ui.page.auction.action

import kotlinx.datetime.LocalDate
import org.evoleq.compose.Markup
import org.evoleq.math.Reader
import org.evoleq.math.contraMap
import org.evoleq.optics.storage.Action
import org.evoleq.optics.transform.merge
import org.evoleq.optics.transform.times
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.auctions
import org.solyton.solawi.bid.application.data.userData
import org.solyton.solawi.bid.module.bid.data.Auction
import org.solyton.solawi.bid.module.bid.data.api.GetAuctions
import org.solyton.solawi.bid.module.user.User
import org.solyton.solawi.bid.module.bid.data.api.Auctions as ApiAuctions

@Markup
fun readAuctions(): Action<Application, GetAuctions, ApiAuctions> = Action(
    name ="ReadAuctions",
    reader = userData * Reader { user: User -> GetAuctions },
    endPoint = GetAuctions::class,
    writer = auctions merge { given, incoming -> given.id == incoming.id }
        contraMap { apiAuctions: ApiAuctions -> apiAuctions.list.map {
            Auction(
                it.id,
                it.name,
                LocalDate(1,1,1)
            )
        }
    }
)