package org.solyton.solawi.bid.application.ui.page.login

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.evoleq.compose.Markup
import org.evoleq.optics.storage.Action
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.actions
import org.solyton.solawi.bid.application.data.userData
import org.solyton.solawi.bid.module.authentication.component.LoginForm
import org.solyton.solawi.bid.module.authentication.data.api.LoggedIn
import org.solyton.solawi.bid.module.authentication.data.api.Login
import org.solyton.solawi.bid.module.user.accessToken
import org.solyton.solawi.bid.module.user.refreshToken
import org.solyton.solawi.bid.application.data.authentication.LoginForm as LoginFormData

@Markup
@Composable
@Suppress("FunctionName")
fun LoginPage(storage: Storage<Application>) {
    LoginForm( storage * LoginFormData ) {
        CoroutineScope(Job()).launch {
            val actions = (storage * actions).read()
            actions.emit(Action(
                "login",
                reader = {app: Application -> Login(app.userData.username, app.userData.password) },
                endPoint = Login::class,
                writer = {loggedIn: LoggedIn -> {app: Application -> app.
                userData{
                    accessToken{ loggedIn.accessToken }.
                    refreshToken{ loggedIn.refreshToken }
                } } }
            ))
        }
    }
}
