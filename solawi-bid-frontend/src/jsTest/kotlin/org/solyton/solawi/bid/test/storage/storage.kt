package org.solyton.solawi.bid.test.storage

import androidx.compose.runtime.*
import org.evoleq.compose.Markup
import org.evoleq.optics.storage.Storage
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.env.Environment
import org.solyton.solawi.bid.module.user.User

@Markup
@Composable
fun TestStorage(): Storage<Application> {
    var pulse by remember { mutableStateOf<Int>(0) }
    // val environment = getEnv()
    var application by remember {
        mutableStateOf<Application>(
            Application(
                environment = Environment("DEV"),
                userData = User("", "", "", "",)
            )
        )
    }

    return Storage<Application>(
        read = { application },
        write = {
                newApplication -> application = newApplication
        }
    )
}