package org.solyton.solawi.bid.application

import org.evoleq.compose.Markup
import org.evoleq.compose.storage.Store
import org.jetbrains.compose.web.renderComposable
import org.solyton.solawi.bid.application.ui.UI
import org.solyton.solawi.bid.application.storage.Storage

@Markup
@Suppress("FunctionName")
fun Application() = renderComposable("root") {
    Store({ Storage() }) {
        UI(this)
    }
}