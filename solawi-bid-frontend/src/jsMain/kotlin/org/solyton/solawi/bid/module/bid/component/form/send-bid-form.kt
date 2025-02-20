package org.solyton.solawi.bid.module.bid.component.form

import androidx.compose.runtime.*
import kotlinx.browser.window
import org.evoleq.compose.Markup
import org.evoleq.compose.label.Label
import org.evoleq.language.Locale
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextInput
import org.solyton.solawi.bid.application.data.device.DeviceType
import org.solyton.solawi.bid.application.ui.style.form.*
import org.solyton.solawi.bid.module.bid.data.Bid
import org.solyton.solawi.bid.module.bid.service.isDouble

@Markup
@Composable
@Suppress("FunctionName")
fun SendBidForm(device: DeviceType, sendBid: (Bid)->Unit)  {

    Div(attrs = {
        style { formStyle(device)() }
    }) {
        var email by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("0.0") }

        Div(attrs = { style { fieldStyle(device)() } }) {
            // todo:i18n
            Label("Email", id = "email", labelStyle = formLabelStyle(device))
            TextInput(email) {
                id("email")
                style { textInputStyle(device)() }
                onInput { email = it.value }
            }
        }
        Div(attrs = { style { fieldStyle(device)() } }) {

            // todo:i18n
            Label("Betrag", id = "amount", labelStyle = formLabelStyle(device))
            TextInput(amount) {
                id("amount")
                style { textInputStyle(device)() }
                onInput {
                    amount = if (it.value.isDouble(Locale.Iso)) {
                        it.value
                    } else {amount}
                }
            }

            Div(attrs = { style { formControlBarStyle(device)() } }) {
                Button(attrs = {
                    style { formButtonStyle(device) }
                    onClick {
                        sendBid(Bid(email, amount.toDouble()))
                    }
                }) {
                    // todo:i18n
                    Text("Gebot senden")
                }
            }
        }
    }
}
