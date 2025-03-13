package org.solyton.solawi.bid.application.ui.page.manual

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.compose.layout.Vertical
import org.evoleq.optics.storage.Storage
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.ui.style.page.verticalPageStyle

@Markup
@Composable
@Suppress("FunctionName")
fun HowToBidPage(application: Storage<Application>) {

    Vertical(verticalPageStyle) {
        H1 { Text("Wie man bietet") }

        H2{ Text("1. Scanne den QR-Code ")}


        H2{ Text("2. Biete")}

        H2{ Text("3. Überprüfe das Ergebnis ")}

        H2{ Text("1. Teile den QR-Code")}



    }
}