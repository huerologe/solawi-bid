package org.evoleq.optics.storage

import kotlinx.serialization.KSerializer
import org.evoleq.ktorx.result.Result
import org.evoleq.ktorx.result.ResultSerializer
import org.evoleq.ktorx.result.Serializer
import org.evoleq.math.Reader
import org.evoleq.math.Writer
import kotlin.reflect.KClass

/**
 * The action takes some input, which can be read from the application data with a reader [Reader<App, S>]
 *
 */
data class Action<Base: Any, out I : Any,  O : Any>(

    val name: String,
    val reader: Reader<in Base, out I>,
    // key of the needed endpoint EndPoint<I,O>
    val endPoint: KClass<*>,
    val writer: Writer<Base, O>,

    val serializer: KSerializer<@UnsafeVariance I>,
    val deserializer: KSerializer<Result<O>>
)

inline fun <Base: Any, reified I : Any, reified O : Any> Action(
    name: String,
    noinline reader: Reader<in Base, out I>,
    // key of the needed endpoint EndPoint<I,O>
    endPoint: KClass<*>,
    noinline writer: Writer<Base, O>,
): Action<Base,I ,  O > = Action(
    name,
    reader,
    endPoint,
    writer ,
    Serializer<I>(),
    ResultSerializer<O>()
)
