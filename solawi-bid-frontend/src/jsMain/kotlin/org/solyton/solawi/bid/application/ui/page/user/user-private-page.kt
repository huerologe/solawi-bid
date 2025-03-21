package org.solyton.solawi.bid.application.ui.page.user

import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.evoleq.compose.Markup
import org.evoleq.compose.layout.Horizontal
import org.evoleq.compose.layout.Property
import org.evoleq.compose.layout.ReadOnlyProperty
import org.evoleq.compose.layout.Vertical
import org.evoleq.language.Lang
import org.evoleq.language.Locale
import org.evoleq.language.component
import org.evoleq.math.emit
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.*
import org.solyton.solawi.bid.application.data.device.mediaType
import org.solyton.solawi.bid.application.service.useI18nTransform
import org.solyton.solawi.bid.application.ui.page.user.action.changePassword
import org.solyton.solawi.bid.application.ui.page.user.i18n.UserLangComponent
import org.solyton.solawi.bid.application.ui.style.page.verticalPageStyle
import org.solyton.solawi.bid.application.ui.style.wrap.Wrap
import org.solyton.solawi.bid.module.control.button.StdButton
import org.solyton.solawi.bid.module.error.component.showErrorModal
import org.solyton.solawi.bid.module.error.lang.errorModalTexts
import org.solyton.solawi.bid.module.i18n.data.language
import org.solyton.solawi.bid.module.i18n.data.locale
import org.solyton.solawi.bid.module.i18n.service.componentOnDemand
import org.solyton.solawi.bid.module.user.data.api.ChangePassword
import org.solyton.solawi.bid.module.user.data.password
import org.solyton.solawi.bid.module.user.data.username
import org.solyton.solawi.bid.module.user.modal.showChangePasswordModal

@Markup
@Composable
@Suppress("FunctionName")
fun PrivateUserPage(storage: Storage<Application>) = Div {
    // Data
    val userData = storage * userData
    val environment = storage * environment
    val i18n = storage * i18N


    // State
    var user by remember { mutableStateOf(ChangePassword("","")) }
    var loading by remember { mutableStateOf(false) }
    // Effect
    LaunchedEffect(Unit) {
        loading = true
        val componentLookup = environment.read().useI18nTransform().componentOnDemand(
            UserLangComponent.UserPrivatePage,
            (i18n * language.get).emit(),
            (i18n * locale.get).emit()
        )
        if(componentLookup.mergeNeeded){
            (i18n * language).write(componentLookup.language)
        }
        loading = false
    }
    // if(loading) return@Div
    // Markup
    Vertical(verticalPageStyle) {
        Wrap {
            Horizontal(styles = { justifyContent(JustifyContent.SpaceBetween); width(100.percent) }) {
                // todo:i18n
                H1 { Text("Persönliche Daten") }
                Horizontal {
                    StdButton(
                        // todo:i18n
                        {"Passwort Ändern"},
                        (storage * deviceData * mediaType.get),
                        false
                    ) {
                        (storage * modals).showChangePasswordModal(
                            texts = ((storage * i18N * language).read() as Lang.Block).component("solyton.auction.updateDialog"),
                            device = storage * deviceData * mediaType.get,
                            storedPassword = (userData * password).read(),
                            setUserData = {password -> user = ChangePassword((userData * username).read() , password) },
                            cancel = {}
                        ) {
                            CoroutineScope(Job()).launch {
                                val action = changePassword(user)
                                val actions = (storage * actions).read()
                                try {
                                    actions.emit( action )
                                } catch(exception: Exception) {
                                    (storage * modals).showErrorModal(
                                        errorModalTexts(exception.message?:exception.cause?.message?:"Cannot Emit action '${action.name}'"),
                                        storage * deviceData * mediaType.get
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Wrap {
            // todo:i18n
            H2{ Text("Deine Daten") }
            Vertical {
                ReadOnlyProperty(Property("Nutzername", (userData * username).read()))

            }
        }
    }
}
