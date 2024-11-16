package org.evoleq.optics.storage

import org.evoleq.math.Reader
import org.evoleq.math.Writer
import kotlin.reflect.KClass

/**
 * The action takes some input, which can be read from the application data with a reader [Reader<App, S>]
 *
 */
data class Action<Base, I : Any, O : Any>(
    val name: String,
    val reader: Reader<Base, I>,
    // key of the needed endpoint EndPoint<I,O>
    val endPoint: KClass<*>,
    val writer: Writer<Base, O>,
)
