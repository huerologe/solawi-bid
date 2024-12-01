package org.solyton.solawi.bid.application.data.authentication

import org.evoleq.language.Lang
import org.evoleq.math.first
import org.evoleq.optics.lens.Lens
import org.evoleq.optics.lens.pX
import org.evoleq.optics.lens.times
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.i18N
import org.solyton.solawi.bid.application.data.userData
import org.solyton.solawi.bid.module.authentication.data.LoginForm
import org.solyton.solawi.bid.module.i18n.data.I18N
import org.solyton.solawi.bid.module.user.User
import org.solyton.solawi.bid.module.authentication.data.User as LoginUser


val LoginForm : Lens<Application, LoginForm> by lazy {
    (userData pX i18N) * Lens<Pair<User, I18N>, LoginForm>(
        get = {pair -> LoginForm(
            LoginUser(
                pair.first.username,
                pair.first.password
            ),
            (pair.second.language ) as Lang.Block
        )},
        set = {loginForm -> {pair ->
            pair.first{ User(
                loginForm.user.username,
                loginForm.user.password,
                "",
                ""
            ) }
        } }
    )
}
