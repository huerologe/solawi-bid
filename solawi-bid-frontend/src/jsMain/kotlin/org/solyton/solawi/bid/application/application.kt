package org.solyton.solawi.bid.application

import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import org.evoleq.compose.Markup
import org.evoleq.compose.storage.Store
import org.evoleq.ktorx.result.on
import org.evoleq.math.Writer
import org.evoleq.math.write
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.renderComposable
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.env.Environment
import org.solyton.solawi.bid.application.data.env.getEnv
import org.solyton.solawi.bid.application.data.env.set
import org.solyton.solawi.bid.application.data.environment
import org.solyton.solawi.bid.application.serialization.installSerializers
import org.solyton.solawi.bid.application.storage.Storage
import org.solyton.solawi.bid.application.storage.event.langLoaded
import org.solyton.solawi.bid.application.storage.event.loadLanguage
import org.solyton.solawi.bid.application.ui.UI
import org.solyton.solawi.bid.application.ui.style.GlobalStyles
import org.solyton.solawi.bid.module.loading.component.Loading

@Markup
@Suppress("FunctionName")
fun Application() = renderComposable("root") {
    installSerializers()
    Style(GlobalStyles)
    Store({ Storage() }) {
        val environmentSet = (this * environment * set).read()
        if(!environmentSet) LaunchedEffect(Unit) { launch {
            val env = try {
                getEnv().copy(set = true)
            } catch (e: Exception) {
                console.error(e.message)
                Environment(
                    true,
                    "prod",
                    backendUrl = "https://bid.solyton.org",
                    backendPort = 8080,
                    frontendUrl = "https://solyton.org",
                    frontendPort = 80
                )
            }
            (this@Store * Writer { envi: Environment ->
                { app: Application -> app.copy(environment = envi) }
            }).write(env) on Unit
        } }
        if(environmentSet) loadLanguage()

        when( langLoaded() && environmentSet ) {
            true -> UI(this)
            false -> Loading()
        }
    }
}