package org.solyton.solawi.bid.application.storage.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.evoleq.optics.storage.Storage
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.env.Environment
import org.solyton.solawi.bid.application.data.env.getEnv

fun Storage<Application>.onReadEnvironment(envSet: Boolean, effect: (Environment)->Unit) {
    if(!envSet) CoroutineScope(Job()).launch {
            effect(
                try {
                    getEnv()
                } catch (e: Exception) {
                    console.error(e.message)
                    Environment( )
                }
            )
        }
    }
