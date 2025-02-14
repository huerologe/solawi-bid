package org.solyton.solawi.bid.application.ui.page.dashboard.data

import org.evoleq.language.LangComponent

sealed class DashboardComponent(override val path: String): LangComponent {
    data object Page : DashboardComponent("solyton.dashboardPage")
}
