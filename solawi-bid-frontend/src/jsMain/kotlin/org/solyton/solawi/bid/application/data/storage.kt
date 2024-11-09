package org.solyton.solawi.bid.application.data

import androidx.compose.runtime.*
import lib.compose.Markup
import lib.optics.storage.Storage
import org.solyton.solawi.bid.application.data.env.Environment
import org.solyton.solawi.bid.application.data.env.getEnv
import org.solyton.solawi.bid.module.user.User


@Markup
@Composable
fun Storage(): Storage<Application> {
    // val environment = getEnv()
    var application by mutableStateOf<Application>(Application(Environment("DEV"), User("", "","", "", )))
    val storage = Storage<Application>(
        read = {application},
        write = {data ->
            application = data
        }
    )
    return storage
}

