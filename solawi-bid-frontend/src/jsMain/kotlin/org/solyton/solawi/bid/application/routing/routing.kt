package org.solyton.solawi.bid.application.routing

import androidx.compose.runtime.Composable
import org.evoleq.compose.routing.Routes
import org.evoleq.compose.routing.Routing
import org.evoleq.compose.routing.RoutingDsl
import org.evoleq.compose.routing.navigate
import org.evoleq.optics.storage.Storage
import org.evoleq.optics.transform.times
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.navbar.navBar
import org.solyton.solawi.bid.application.data.userData
import org.solyton.solawi.bid.application.service.isLoggerIn
import org.solyton.solawi.bid.application.ui.page.dashboard.DashboardPage
import org.solyton.solawi.bid.application.ui.page.login.LoginPage
import org.solyton.solawi.bid.application.ui.page.test.TestPage
import org.solyton.solawi.bid.module.navbar.component.NavBar

@RoutingDsl
@Composable
@Suppress("FunctionName")
fun Routing(storage: Storage<Application>): Routes = Routing("/") {
    route("login") {
        component { LoginPage(storage) }
    }
    route("solyton") {
        wrap {
            access{
                when((storage * userData).read().isLoggerIn()) {
                    true -> true
                    false -> {
                        navigate("/login")
                        false
                    }
                }
            }
            layout {
                {
                    NavBar(storage * navBar)
                    // render page
                    it()
                }
            }

            route("dashboard") {
                component { DashboardPage(storage) }
            }

            route("test") {
                component { TestPage() }
            }
        }
    }
}