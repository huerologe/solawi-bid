package org.evoleq.compose.routing

import androidx.compose.runtime.*
import kotlinx.browser.window
import org.evoleq.compose.Markup
import org.w3c.dom.Location

interface Configuration<out T> {
    fun configure(): T
}

// @DslMarker annotation class RoutingDsl
typealias RoutingDsl = Markup

class RouteConfiguration : Configuration<Route> {

    private val segments : ArrayList<RouteSegment> = arrayListOf()
    private val queryParameters: ArrayList<Parameter> = arrayListOf()

    override fun configure(): Route = Route(
        segments,
        queryParameters
    )
}

class RoutesConfiguration : Configuration<Routes> {

    lateinit var segment: RouteSegment
    var component: (@Composable ComposableRoute.()->Unit)? = null
    var wrap: (@Composable ComposableRoute.()->Unit)? = null

    val routes: ArrayList<Routes> = arrayListOf()

    override fun configure(): Routes = Routes(
        segment,
        routes,
        component
    )

    private fun extract(name: String): String = name.dropWhile { it == '/' }.dropLastWhile { it == '/' }

    @RoutingDsl
    fun route(path: String, routesConfiguration: RoutesConfiguration.()->Unit) {
        val cleanPath = extract(path)
        if("/" in cleanPath) {
            route(path.split("/"), routesConfiguration)
        } else {
            val newSegment = Segment().run(cleanPath).result!!
            val newRoutes = with(RoutesConfiguration()) {
                routesConfiguration()
                segment = newSegment
                configure()
            }
            if (newSegment is RouteSegment.Variable) {
                routes.add(newRoutes)
            }
            if (newSegment is RouteSegment.Static) {
                routes.add(0, newRoutes)
            }
        }
    }

    private fun route(path: List<String>, routesConfiguration: RoutesConfiguration.() -> Unit) {
        if(path.size == 1) {
            route(path.first(),routesConfiguration)
        } else {
            val first = path.first()
            val tail = path.drop(1)

            val newSegment = Segment().run(first).result!!
            val newRoutes = with(RoutesConfiguration()) {
                route(tail, routesConfiguration)
                segment = newSegment
                configure()
            }
            if (newSegment is RouteSegment.Variable) {
                routes.add(newRoutes)
            }
            if (newSegment is RouteSegment.Static) {
                routes.add(0, newRoutes)
            }
        }
    }

    @Markup
    fun component(content: @Composable ComposableRoute.()->Unit) {
        component = content
    }

    @Markup
    fun wrap(wrapper: @Composable ComposableRoute.()->Unit) {
        wrap = wrapper
    }
}

@RoutingDsl
fun routing(
    routes: RoutesConfiguration.()->Unit
): Routes = with(RoutesConfiguration()) {
    routes()
    segment = RouteSegment.Root
    configure()
}
