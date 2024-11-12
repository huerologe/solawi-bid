package org.solyton.solawi.bid.application.storage

import androidx.compose.runtime.*
import org.evoleq.compose.Markup
import org.evoleq.optics.storage.Storage
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.env.Environment
//import org.solyton.solawi.bid.application.data.env.getEnv
import org.solyton.solawi.bid.module.user.User


@Markup
@Composable
fun Storage(): Storage<Application> {
    // val environment = getEnv()
    var application by mutableStateOf<Application>(Application(
        environment = Environment("DEV"),
        userData = User("", "","", "", )
    ))
    val storage = Storage<Application>(
        read = {application},
        write = {data ->
            application = data
        }
    )
    return storage
}

