package org.solyton.solawi.bid.application.storage.event

import org.evoleq.compose.routing.navigate
import org.evoleq.optics.storage.Storage
import org.solyton.solawi.bid.application.data.Application

fun Storage<Application>.onLogin(oldApplication: Application, newApplication: Application) {
    if(newApplication.userData != oldApplication.userData && newApplication.userData.accessToken != "") {
        console.log(newApplication.userData)
        navigate("/solyton/dashboard")
    }
}