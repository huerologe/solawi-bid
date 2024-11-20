package org.solyton.solawi.bid.module.authentication.component

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.compose.label.Label
import org.evoleq.language.Lang
import org.evoleq.language.component
import org.evoleq.language.get
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.solyton.solawi.bid.module.authentication.data.*

@Markup
@Composable
@Suppress("FunctionName")
fun LoginForm(storage: Storage<LoginForm>, login: ()->Unit) {
    val userData = storage * user
    val texts = (storage * texts).read() as Lang.Block
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
                        login()
                    }
                }) {
                    Text(buttonTexts["ok"])
                }
            }
        }
    }
}
