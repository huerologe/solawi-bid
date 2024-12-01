package org.solyton.solawi.bid.api

import org.evoleq.ktorx.api.Api
import org.solyton.solawi.bid.module.authentication.data.api.*
import org.solyton.solawi.bid.module.bid.data.api.Auction
import org.solyton.solawi.bid.module.bid.data.api.GetAuctions
import org.solyton.solawi.bid.module.bid.data.api.PreAuction

val solawiApi by lazy {
    // Authentication
    Api().post<Login, LoggedIn>(
        key = Login::class,
        url = "login"
    ).post<RefreshToken,LoggedIn>(
        key = RefreshToken::class,
        url = "refresh"
    ).post<Logout, Unit>(
        key = Logout::class,
        url = "logout"
    )

    // Auction
    .post<PreAuction, Auction>(
        key = PreAuction::class,
        url = "auction/create"
    ).get<GetAuctions, List<Auction>>(
        key = GetAuctions::class,
        url = "auction/all"
    )
}
