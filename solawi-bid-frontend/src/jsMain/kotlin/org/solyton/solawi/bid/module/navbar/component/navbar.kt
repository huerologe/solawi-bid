package org.solyton.solawi.bid.module.navbar.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import org.evoleq.compose.Markup
import org.evoleq.compose.routing.navigate
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.jetbrains.compose.web.attributes.selected
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.solyton.solawi.bid.module.i18n.data.locale
import org.solyton.solawi.bid.module.i18n.data.locales
import org.solyton.solawi.bid.module.navbar.data.NavBar
import org.solyton.solawi.bid.module.navbar.data.i18n

@Markup
@Composable
@Suppress("FunctionName")
fun NavBar(
    navBar: Storage<NavBar>
) = Div({
    style {
        paddingTop(10.px)
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.FlexEnd)
    }
}) {

    val i18n = navBar * i18n
    val currentLocale = (i18n * locale).read()
    val scope = rememberCoroutineScope()


    Button({onClick {
        navigate("/")
    }}) { Text("Home") }

    Button({onClick {
        navigate("/solyton/dashboard")
    }}) { Text("Dashboard") }

    Div({style { width(50.px) }}) {  }

    Div({classes("select")}) {
        Select {
            (i18n * locales).read().forEach { s ->
                Option(s, {
                    if (s == currentLocale) {
                        selected()
                    }
                    onClick {
                        scope.launch {
                            (i18n * locale).write(s)
                        }
                    }
                }) {
                    // Text(((i18n * language).read() as Lang.Block).component("hanoi.locales")[s])
                }
            }
        }
    }
}