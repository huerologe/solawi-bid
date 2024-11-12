package org.solyton.solawi.bid.application

import org.evoleq.compose.Markup
import org.evoleq.compose.storage.Store
import org.evoleq.ktorx.result.serializers
import org.jetbrains.compose.web.renderComposable
import org.solyton.solawi.bid.application.serialization.installSerializers
import org.solyton.solawi.bid.application.ui.UI
import org.solyton.solawi.bid.application.storage.Storage
import org.solyton.solawi.bid.application.storage.langLoaded
import org.solyton.solawi.bid.module.loading.component.Loading

@Markup
@Suppress("FunctionName")
fun Application() = renderComposable("root") {
    installSerializers()

    Store({ Storage() }) {
        when( langLoaded() ) {
            true -> UI(this)
            false -> Loading()
        }
    }
}