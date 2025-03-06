package org.solyton.solawi.bid.module.bid.component.form

import androidx.compose.runtime.*
import io.ktor.util.*
import org.evoleq.compose.Markup
import org.evoleq.compose.label.Label
import org.evoleq.language.Locale
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.dom.*
import org.solyton.solawi.bid.application.data.device.DeviceType
import org.solyton.solawi.bid.application.ui.style.GlobalStyles.style
import org.solyton.solawi.bid.application.ui.style.form.*
import org.solyton.solawi.bid.module.bid.data.Bid
import org.solyton.solawi.bid.module.bid.service.isDouble
import org.solyton.solawi.bid.module.bid.service.toDecimal
import org.solyton.solawi.bid.module.control.button.SubmitButton

@Markup
@Composable
@Suppress("FunctionName")
fun SendBidForm(device: DeviceType, sendBid: (Bid)->Unit)  {

    Div(attrs = {
        style { formStyle(device)() }
    }) {
        // State
        var email by remember { mutableStateOf("") }
        var emailValid by remember { mutableStateOf(true) }
        var amount by remember { mutableStateOf("0,00") }
        var amountValid by remember { mutableStateOf(true) }

        Div(attrs = { style { fieldStyle(device)() } }) {
            // todo:i18n
            Label("Email", id = "email", labelStyle = formLabelStyle(device))
            TextInput(email) {
                id("email")
                style { textInputStyle(device)() }
                onInput {
                    email = it.value
                    emailValid = it.value.isEmail()
                }
            }
            // todo:i18n
            if(!emailValid) {P(attrs={style{color(Color.crimson)}}){Text("Keine valide email Adresse")}}else{P{Text(" ")}}
        }
        Div(attrs = { style { fieldStyle(device)() } }) {

            // todo:i18n
            Label("Betrag", id = "amount", labelStyle = formLabelStyle(device))
            TextInput(amount) {
                id("amount")
                style { textInputStyle(device)() }
                onInput {
                    amount = it.value
                    amountValid = it.value.isDouble()
                }
            }
            // todo:i18n
            if(!amountValid) {P(attrs={style{color(Color.crimson)}}){Text("Kein valider Betrag")}}else{P{Text(" ")}}
            Div(attrs = { style { formControlBarStyle(device)() } }) {
                SubmitButton(
                    {"Gebot senden"},
                    device,
                    !(amountValid && emailValid)
                ) {
                    sendBid(
                        Bid(
                            email.toLowerCasePreservingASCIIRules(),
                            amount.toDecimal()
                        )
                    )
                }
            }

        }
    }
}

fun String.isEmail(): Boolean {
    val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    return regex.matches(this)
}
