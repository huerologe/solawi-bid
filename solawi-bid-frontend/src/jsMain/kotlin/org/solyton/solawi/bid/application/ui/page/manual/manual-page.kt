package org.solyton.solawi.bid.application.ui.page.manual

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.optics.storage.Storage
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application

@Composable
@Markup
@Suppress("FunctionName")
fun ManualPage(application: Storage<Application>) {

    H1 { Text("Betriebsanleitung") }

    H2 { "Inhaltsverzeichnis" }


}