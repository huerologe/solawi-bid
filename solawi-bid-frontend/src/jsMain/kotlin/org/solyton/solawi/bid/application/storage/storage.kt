package org.solyton.solawi.bid.application.storage

import androidx.compose.runtime.*
import org.evoleq.compose.Markup
import org.evoleq.compose.storage.onInit
import org.evoleq.math.state.runOn
import org.evoleq.optics.storage.Action
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.storage.onChange
import org.evoleq.optics.transform.times
import org.solyton.solawi.bid.application.api.CallApi
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.actions
import org.solyton.solawi.bid.application.data.env.Environment
import org.solyton.solawi.bid.application.storage.event.*
import org.solyton.solawi.bid.module.user.User
import kotlin.reflect.KClass


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
    )
    .onInit {
        checkCookie()
        loadLanguage()
    }
    .onChange { oldApplication, newApplication ->
        onCookieDisclaimerConfirmed(oldApplication, newApplication)
        onLocaleChanged(oldApplication, newApplication)
        pulse++
    }.onDispatch {
        (this@onDispatch * actions).read().collect { action : Action<Application, *, *>->
            //require(action.reader is Reader<Application, Any>)
            //require(action.writer is Writer<Application, *>)
            CallApi(action
            ) runOn this
        }
    }
}


inline fun <reified T> type(obj: Any): T? = when(obj) {
    is T -> obj as T
    else -> null
}

inline fun <T : Any> type(obj: Any, clazz: KClass<T>): T? = when {
    obj::class == clazz -> obj as T
    else -> null
}
