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
fun SendBidForm(sendBid: (Bid)->Unit)  {
    var screenWidth by remember { mutableStateOf(window.innerWidth) }

    // ✅ Listen to window resize and update screenWidth
    LaunchedEffect(Unit) {
        window.addEventListener("resize", {
            screenWidth = window.innerWidth
        })
    }
    val device = DeviceType.from(screenWidth.toDouble())
    Div(attrs = {

        style { formStyle(device)() }
        // classes(formStyle)
    }) {



        var email by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("0.0") }

        Div(attrs = { style { fieldStyle() } }) {
            Label("Email", id = "email", labelStyle = formLabelStyle)
            TextInput(email) {
                id("email")
                style { textInputStyle() }
                onInput { email = it.value }
            }
        }
        Div(attrs = { style { fieldStyle() } }) {
            Label("Betrag", id = "amount", labelStyle = formLabelStyle)
            TextInput(amount) {
                id("amount")
                style { textInputStyle() }
                onInput {
                    console.log(it.value)
                    amount = if (it.value.isDouble(Locale.Iso)) {
                        it.value
                    } else {amount}
                }
            }

            Div(attrs = { style { formControlBarStyle() } }) {
                Button(attrs = {
                    onClick {
                        sendBid(Bid(email, amount.toDouble()))
                    }
                }) {
                    Text("Gebot senden")
                }
            }
        }
    }
}

