package org.solyton.solawi.bid.application.ui.page.dashboard

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.compose.routing.navigate
import org.evoleq.optics.storage.Storage
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application

@Markup
@Composable
@Suppress("FunctionName")
fun DashboardPage(storage: Storage<Application>) {
    Text("Welcome to the dashboard")
    Br()
    Button(
        attrs = {
            onClick {
                /*
                CoroutineScope(Job()).launch {
                //LaunchedEffect(Unit) {
                    (storage * actions).read().emit(readAuctions())
                }

                 */
                navigate("/solyton/auctions")
            }
        }
    ) {
        Text("Auctions")
    }
}