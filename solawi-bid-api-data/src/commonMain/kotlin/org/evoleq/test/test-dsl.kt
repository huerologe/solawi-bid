package org.evoleq.test

import org.evoleq.math.MathDsl

@MathDsl
suspend fun String.testCase(name: String, test: suspend Any.()->Unit): Unit =
    if(name == this) test() else Unit
@MathDsl
suspend fun String.testGroup(group: (String)->Boolean, test: suspend Any.() -> Unit) =
    if(group(this)) test() else Unit

@MathDsl
suspend fun test(case: String, test: suspend String.() -> Unit): Unit = with(case){test()}