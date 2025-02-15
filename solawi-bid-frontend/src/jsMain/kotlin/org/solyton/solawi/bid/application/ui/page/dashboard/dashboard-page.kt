package org.solyton.solawi.bid.application.ui.page.dashboard

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.compose.routing.navigate
import org.evoleq.language.Lang
import org.evoleq.language.component
import org.evoleq.language.subComp
import org.evoleq.language.title
import org.evoleq.math.Source
import org.evoleq.math.emit
import org.evoleq.math.times
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.i18N
import org.solyton.solawi.bid.application.ui.page.dashboard.data.DashboardComponent
import org.solyton.solawi.bid.module.i18n.data.language

@Markup
@Composable
@Suppress("FunctionName")
fun DashboardPage(storage: Storage<Application>) {
    // Effects

    // Data

    // Texts
    val texts = (storage * i18N * language * component(DashboardComponent.Page))
    val auctionsCard = texts * subComp("auctionsCard")

    H1 { Text((texts * title).emit()) }
    Br()
    // auctionsCard
    AuctionsCard(
        storage = storage,
        texts = auctionsCard
    )

}

@Markup
@Composable
@Suppress("FunctionName")
fun AuctionsCard(storage: Storage<Application>, texts: Source<Lang.Block>) {
    val navButton = texts * subComp("navButton")
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
        Text((navButton * title).emit())
    }
}