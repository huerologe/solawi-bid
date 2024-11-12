package org.solyton.solawi.bid.application.storage

import androidx.compose.runtime.*
import org.evoleq.compose.Markup
import org.evoleq.compose.storage.onInit
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.storage.onChange
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.env.Environment
import org.solyton.solawi.bid.module.user.User


@Markup
@Composable
fun Storage(): Storage<Application> {
    var pulse by remember { mutableStateOf<Int>(0) }
    // val environment = getEnv()
    var application by remember{ mutableStateOf<Application>(Application(
        environment = Environment("DEV"),
        userData = User("", "","", "", )
    ))}

    return Storage<Application>(
        read = { application },
        write = {
            newApplication -> application = newApplication
        }
    ).onChange { oldApplication, newApplication ->
        onCookieDisclaimerConfirmed(oldApplication, newApplication)
        onLocaleChanged(oldApplication, newApplication)
        pulse++
    }.onInit {
        checkCookie()
        loadLanguage()
    }
}


