package org.evoleq.maths.state

import lib.optics.storage.Storage
import org.evoleq.math.MathDsl
import org.jetbrains.compose.web.css.CSSUnit
import org.solyton.solawi.bid.application.data.Application

typealias AppState<T> = State<Application, T>
typealias KlAppState<S, T> = KlState<Application, S, T>
typealias StorageState<T> = State<Storage<Application>, T>
typealias KlStorageState<S, T> = KlState<Storage<Application>, S, T>

@MathDsl
@Suppress("FunctionName")
fun <T>  AppState(state: suspend (Application) -> Pair<T, Application>): State<Application, T> = State{ app -> state(app)}

@MathDsl
@Suppress("FunctionName")
fun <S, T> KlAppState(klState: (S)-> AppState<T>) = KlState(klState)

@MathDsl
@Suppress("FunctionName")
fun <T> StorageState(state: suspend (Storage<Application>) -> Pair<T, Storage<Application>>): State<Storage<Application>, T> = State{
    storage -> state(storage)
}

@MathDsl
@Suppress("FunctionName")
fun <S, T> KlStorageState(klState: suspend (S) -> StorageState<T>): KlState<Storage<Application>, S, T> = KlState (klState)
