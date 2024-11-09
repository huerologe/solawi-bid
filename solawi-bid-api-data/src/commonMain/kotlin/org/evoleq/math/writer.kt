package org.evoleq.math

typealias Writer<W, P> = (P)->(W)->W

infix fun <W, Q, P> Writer<W, P>.contraMap(f: (Q)->P): Writer<W, Q> = {q -> this(f(q))}

// operator fun <W, P, Q> Writer<W, P>.times(other: Writer<P, Q>): Writer<W, Q> = {q -> {w -> contraMap (other(q)) }