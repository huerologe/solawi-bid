package org.solyton.solawi.bid.application.ui.page.manual

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.compose.layout.Vertical
import org.evoleq.optics.storage.Storage
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.ui.style.page.verticalPageStyle

@Markup
@Composable
@Suppress("FunctionName")
fun HowToBidPage(application: Storage<Application>) {

    Vertical(verticalPageStyle) {
        H1 { Text("Wie man bietet") }


    }
}