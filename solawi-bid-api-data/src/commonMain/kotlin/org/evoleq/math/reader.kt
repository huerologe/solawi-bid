package org.evoleq.math

typealias Reader<E, T> = (E)->T

@MathDsl
infix fun <E, S, T> Reader<E, S>.map(f: (S)->T): Reader<E, T> = {e -> f (this(e))}

@MathDsl
infix fun <E, T> Reader<E, T>.readFrom(e: E): T = this(e)
@MathDsl
fun <T> Reader<Unit, T>.read(): T = this(Unit)
