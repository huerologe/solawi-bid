package org.solyton.solawi.bid.application.storage.event

import org.evoleq.compose.routing.navigate
import org.evoleq.optics.storage.Storage
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.module.localstorage.api.write

fun Storage<Application>.onLogin(oldApplication: Application, newApplication: Application) {
    if(newApplication.userData != oldApplication.userData && newApplication.userData.accessToken != "") {
        write("accessToken", newApplication.userData.accessToken)
        write("refreshToken", newApplication.userData.refreshToken)
        navigate("/solyton/dashboard")
    }
}
