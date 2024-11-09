package org.solyton.solawi.bid.application

import lib.compose.Markup
import lib.compose.storage.Store
import org.jetbrains.compose.web.renderComposable
import org.solyton.solawi.bid.application.ui.UI
import org.solyton.solawi.bid.application.data.Storage

@Markup
@Suppress("FunctionName")
fun Application() = renderComposable("root") {
    Store({ Storage() }) {
        UI(this)
    }
}