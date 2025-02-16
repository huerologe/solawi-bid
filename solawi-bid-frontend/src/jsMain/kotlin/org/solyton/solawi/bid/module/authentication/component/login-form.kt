package org.solyton.solawi.bid.module.authentication.component

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.compose.label.Label
import org.evoleq.language.Lang
import org.evoleq.language.component
import org.evoleq.language.get
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.dom.*
import org.solyton.solawi.bid.application.ui.style.form.*
import org.solyton.solawi.bid.module.authentication.data.*

@Markup
@Composable
@Suppress("FunctionName")
fun LoginForm(storage: Storage<LoginForm>, login: ()->Unit) {
    val userData = storage * user
    val texts = (storage * texts).read() as Lang.Block
    val loginFields = texts.component("solyton.authentication.login.fields")
    val device = (storage * deviceType).read()

    Div(attrs = {
        style { formStyle(device)() }
    }) {
        Div(attrs = { style { fieldStyle() } }) {
            Label(loginFields["username"], id = "username", labelStyle = formLabelStyle)

            TextInput((userData * username).read()) {
                style { textInputStyle() }
                id("username")
                onInput {
                    (userData * username).write(it.value)
                }
            }
        }
        Div(attrs = { style { fieldStyle() } }) {
            Label(loginFields["password"], id = "password", labelStyle = formLabelStyle)
            PasswordInput((userData * password).read()) {
                style { textInputStyle() }
                id("password")
                onInput { (userData * password).write(it.value) }
            }
        }

        Div(attrs = {style { formControlBarStyle() }}) {
            val buttonTexts = texts.component("solyton.authentication.login.buttons")

            Button(attrs = {
                onClick {
                    login()
                }
            }) {
                Text(buttonTexts["ok"])
            }
            Button{
                Text("Registrieren")
            }
        }
    }
}

