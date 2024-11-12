package org.solyton.solawi.bid.application.storage

import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.evoleq.compose.Markup
import org.evoleq.compose.storage.initialize
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.evoleq.language.LanguageP
import org.evoleq.language.Lang
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.env.Environment
import org.solyton.solawi.bid.application.data.environment
import org.solyton.solawi.bid.application.data.i18N
import org.solyton.solawi.bid.application.data.isCookieDisclaimerConfirmed
import org.solyton.solawi.bid.module.cookie.api.readCookie
import org.solyton.solawi.bid.module.cookie.api.writeCookie
import org.solyton.solawi.bid.module.cookie.api.writeLang
import org.solyton.solawi.bid.module.i18n.api.i18n
import org.solyton.solawi.bid.module.i18n.data.language
import org.solyton.solawi.bid.module.i18n.data.locale
import org.solyton.solawi.bid.module.i18n.data.locales
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

    val storage = Storage<Application>(
        read = {
            application
        },
        write = { newApplication -> //if (application != newApplication) {
                val oldApplication = application
                application = newApplication

                if (newApplication.isCookieDisclaimerConfirmed != oldApplication.isCookieDisclaimerConfirmed) {
                    console.log("storage: isCookieDisclaimerConfirmed = ${newApplication.isCookieDisclaimerConfirmed}")
                    if (newApplication.isCookieDisclaimerConfirmed) {
                        CoroutineScope(Job()).launch {
                            writeCookie()
                        }
                    }
                }
                if (newApplication.i18N.locale != oldApplication.i18N.locale) {
                    CoroutineScope(Job()).launch {
                        try {
                            with(LanguageP().run(application.environment.i18n(newApplication.i18N.locale)).result) {
                                if (this != null) {
                                    application = application.copy(
                                        i18N = application.i18N.copy(
                                            locale = newApplication.i18N.locale,
                                            language = this
                                        )
                                    )
                                    writeLang(newApplication.i18N.locale)

                                }
                            }
                        } catch (exception: Exception) {
                            console.log(exception)
                        }
                    }
                }
                pulse++
           // }
        }
    )
    initialize {
        val cookie = readCookie()
        if(cookie != null){
            (storage * isCookieDisclaimerConfirmed).write(true)
        }

        val languageStorage = (storage * i18N * language)
        val localesStorage = (storage * i18N * locales)
        val localeStorage = (storage * i18N * locale)
        val environment = (storage * environment)

        val langLoaded: () -> Boolean = {
            (languageStorage.read() as Lang.Block).value.isNotEmpty() &&
                localesStorage.read().isNotEmpty()
        }

        if (!langLoaded()) {
            LaunchedEffect(Unit) {
                val rawLocale = environment.read().i18n(localeStorage.read())
                val locale = LanguageP().run(rawLocale).result
                with(locale) {
                    if (this != null) {
                        languageStorage.write(this)
                    }
                }
                with(LanguageP().run(environment.read().i18n("locales")).result) {
                    if (this != null) {
                        localesStorage.write((this as Lang.Block).value.map { it.key })
                    }
                }
            }
        }
    }

    return storage
}

fun Storage<Application>.langLoaded (): Boolean  {

    val languageStorage = (this * i18N * language)
    val localesStorage = (this * i18N * locales)

    return (languageStorage.read() as Lang.Block).value.isNotEmpty() &&
        localesStorage.read().isNotEmpty()
}

