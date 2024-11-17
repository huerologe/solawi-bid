package org.solyton.solawi.bid.application.ui.page.login

import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.evoleq.compose.Markup
import org.evoleq.compose.label.Label
import org.evoleq.language.Lang
import org.evoleq.language.component
import org.evoleq.language.get
import org.evoleq.optics.storage.Action
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.actions
import org.solyton.solawi.bid.application.data.i18N
import org.solyton.solawi.bid.application.data.userData
import org.solyton.solawi.bid.module.authentication.data.api.LoggedIn
import org.solyton.solawi.bid.module.authentication.data.api.Login
import org.solyton.solawi.bid.module.i18n.data.language
import org.solyton.solawi.bid.module.user.accessToken
import org.solyton.solawi.bid.module.user.password
import org.solyton.solawi.bid.module.user.refreshToken
import org.solyton.solawi.bid.module.user.username

@Markup
@Composable
@Suppress("FunctionName")
fun LoginPage(storage: Storage<Application>) {
    val userData = storage * userData
    val texts = (storage * i18N * language).read() as Lang.Block
    val loginFields = texts.component("solyton.authentication.login.fields")
    Div(attrs = {
        style{
            marginTop(20.pc)
            display(DisplayStyle("flex"))
            justifyContent(JustifyContent.Center)
        }
    }) {
        Div(attrs = {
            style {
                width(400.px)
                height(300.px)

            }
        }) {
            // Flex {
            Div {
                Label(loginFields["username"], id = "username")
                Br()
                TextInput((userData * username).read()) {
                    id("username")
                    onInput {
                        (userData * username).write(it.value)
                    }
                }
                Br()
                Label(loginFields["password"], id = "password")
                Br()
                PasswordInput((userData * password).read()) {
                    id("password")
                    onInput { (userData * password).write(it.value) }
                }
            }
            Div {
                val buttonTexts = texts.component("solyton.authentication.login.buttons")

                Button(attrs = {
                    onClick {
                        CoroutineScope(Job()).launch {
                            val actions = (storage * actions).read()
                            actions.emit(Action(
                                "login",
                                reader = {app:Application -> Login(app.userData.username, app.userData.password)},
                                endPoint = Login::class,
                                writer = {loggedIn: LoggedIn -> {app: Application -> app.
                                    userData{
                                        accessToken{ loggedIn.accessToken }.
                                        refreshToken{ loggedIn.refreshToken }
                                } } }
                            ))
                        }
                    }
                }) {
                    Text(buttonTexts["ok"])
                }
            }
        }
    }
}
