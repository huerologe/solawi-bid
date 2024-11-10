package org.solyton.solawi.bid.api

import org.evoleq.ktorx.api.Api
import org.solyton.solawi.bid.module.authentication.data.api.LoggedIn
import org.solyton.solawi.bid.module.authentication.data.api.Login
import org.solyton.solawi.bid.module.authentication.data.api.Logout
import org.solyton.solawi.bid.module.authentication.data.api.RefreshToken

val solawiApi by lazy {
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
}
