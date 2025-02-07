package org.solyton.solawi.bid.application

import androidx.compose.runtime.*
import org.evoleq.compose.Markup
import org.evoleq.compose.storage.Store
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.renderComposable
import org.solyton.solawi.bid.application.data.env.Environment
import org.solyton.solawi.bid.application.data.env.getEnv
import org.solyton.solawi.bid.application.serialization.installSerializers
import org.solyton.solawi.bid.application.storage.Storage
import org.solyton.solawi.bid.application.storage.event.langLoaded
import org.solyton.solawi.bid.application.ui.UI
import org.solyton.solawi.bid.application.ui.style.GlobalStyles
import org.solyton.solawi.bid.module.loading.component.Loading

@Markup
@Suppress("FunctionName")
fun Application() = renderComposable("root") {
    installSerializers()
    Style(GlobalStyles)
    Store({ Storage() }) {
        when( langLoaded() ) {
            true -> UI(this)
            false -> Loading()
        }
    }
}