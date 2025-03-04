package org.solyton.solawi.bid.application.ui.page.auction

import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.evoleq.compose.Markup
import org.evoleq.compose.label.Label
import org.evoleq.compose.layout.Horizontal
import org.evoleq.compose.layout.Vertical
import org.evoleq.math.emit
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.*
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.actions
import org.solyton.solawi.bid.application.data.bidderMailAddresses
import org.solyton.solawi.bid.application.data.device.mediaType
import org.solyton.solawi.bid.application.data.deviceData
import org.solyton.solawi.bid.application.ui.page.auction.action.readAuctions
import org.solyton.solawi.bid.application.ui.page.auction.action.searchUsernameOfBidder
import org.solyton.solawi.bid.application.ui.style.form.fieldStyle
import org.solyton.solawi.bid.application.ui.style.form.formLabelStyle
import org.solyton.solawi.bid.application.ui.style.form.formStyle
import org.solyton.solawi.bid.application.ui.style.form.textInputStyle
import org.solyton.solawi.bid.application.ui.style.page.verticalPageStyle
import org.solyton.solawi.bid.application.ui.style.wrap.Wrap
import org.solyton.solawi.bid.module.bid.data.api.SearchBidderData
import org.solyton.solawi.bid.module.control.button.StdButton

@Markup
@Composable
@Suppress("FunctionName")
fun SearchBiddersPage(storage: Storage<Application>) = Div {

    // State
    var firstname by remember{ mutableStateOf("") }
    var lastname by remember{ mutableStateOf("") }
    var email by remember{ mutableStateOf("") }
    var emails by remember{ mutableStateOf("") }
    var data by remember{ mutableStateOf("") }

    var foundEmails by remember{ mutableStateOf(listOf<String>()) }
    var auctionId by remember{ mutableStateOf("")}
    // Effect
    LaunchedEffect(Unit) {
        (storage * actions).read().emit(readAuctions())
    }
    // Data
    //val auction = auctions * FirstBy { it.auctionId == auctionId }
    val device = storage * deviceData * mediaType.get

    // Markup
    Vertical(verticalPageStyle) {
        Wrap { Horizontal(styles = { justifyContent(JustifyContent.SpaceBetween); width(100.percent) }) {

            // todo:i18n
            H1 { Text("Bieter Suche") }//Text(with((storage * auction).read()) { name }) }
            Horizontal {
                // todo:i18n
                StdButton({ "Search" }, device) {
                    val searchBidderData = SearchBidderData(
                        firstname = firstname,
                        lastname = lastname,
                        email = email,
                        relatedEmails = emails.split(",").map{it.trim()}.filter { it.isNotBlank() },
                        relatedNames = listOf()
                    )
                    CoroutineScope(Job()).launch {
                        (storage * actions).read().emit(searchUsernameOfBidder(searchBidderData))
                    }
                }
            }
        } }
        Wrap {
            Div(attrs = {
                style { formStyle(device.emit())() }
            }) {
                Div(attrs = { style { fieldStyle(device.emit())() } }) {
                    // todo:i18n
                    Label("Vorname", id = "firstname", labelStyle = formLabelStyle(device.emit()))
                    TextInput(firstname) {
                        style { textInputStyle(device.emit())() }
                        id("firstname")
                        onInput {
                            firstname = it.value
                        }
                    }
                }

                Div(attrs = { style { fieldStyle(device.emit())() } }) {
                    // todo:i18n
                    Label("Nachname", id = "lastname", labelStyle = formLabelStyle(device.emit()))
                    TextInput(lastname) {
                        style { textInputStyle(device.emit())() }
                        id("lastname")
                        onInput {
                            lastname = it.value
                        }
                    }
                }

                Div(attrs = { style { fieldStyle(device.emit())() } }) {
                    // todo:i18n
                    Label("Email", id = "email", labelStyle = formLabelStyle(device.emit()))
                    TextInput(email) {
                        style { textInputStyle(device.emit())() }
                        id("email")
                        onInput {
                            email = it.value
                        }
                    }
                }

                Div(attrs = { style { fieldStyle(device.emit())() } }) {
                    // todo:i18n
                    Label("Emails", id = "emails", labelStyle = formLabelStyle(device.emit()))
                    TextInput(emails) {
                        style { textInputStyle(device.emit())() }
                        id("emails")
                        onInput {
                            emails = it.value
                        }
                    }
                }

                Div(attrs = { style { fieldStyle(device.emit())() } }) {
                    // todo:i18n
                    Label("Data", id = "data", labelStyle = formLabelStyle(device.emit()))
                    TextInput(data) {
                        style { textInputStyle(device.emit())() }
                        id("data")
                        onInput {
                            data = it.value
                        }
                    }
                }
            }

        }
        Wrap{
            // todo:i18n
            H2{Text("Emails")}
            (storage * bidderMailAddresses.get).emit().emails.forEach {
                P{ Text(it) }
            }
        }
    }
}
