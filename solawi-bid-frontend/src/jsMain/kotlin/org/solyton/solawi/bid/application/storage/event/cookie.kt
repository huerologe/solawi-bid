package org.solyton.solawi.bid.application.storage.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.isCookieDisclaimerConfirmed
import org.solyton.solawi.bid.module.cookie.api.readCookie
import org.solyton.solawi.bid.module.cookie.api.writeCookie

fun Storage<Application>.onCookieDisclaimerConfirmed(oldApplication: Application, newApplication: Application) {
    if (newApplication.isCookieDisclaimerConfirmed != oldApplication.isCookieDisclaimerConfirmed) {
        if (newApplication.isCookieDisclaimerConfirmed) {
            CoroutineScope(Job()).launch {
                writeCookie()
            }
        }
    }
}


fun Storage<Application>.checkCookie(){
    val storage = this
    val cookie = readCookie()
    if(cookie != null){
        (storage * isCookieDisclaimerConfirmed).write(true)
    }
}
