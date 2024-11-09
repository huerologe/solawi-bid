package org.solyton.solawi.bid.application.ui

import androidx.compose.runtime.Composable
import lib.compose.Markup
import lib.optics.storage.Storage
import lib.optics.transform.times
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.env.type
import org.solyton.solawi.bid.application.data.environment

@Markup
@Suppress("FunctionName")
@Composable fun UI(storage: Storage<Application>) {

    val environment = (storage * environment * type).read()

    Text("Environment: $environment")
}