package org.evoleq.ktorx.result

import kotlinx.serialization.Serializable
import org.evoleq.math.MathDsl


@Serializable(with = ResultSerializer::class)
sealed class Result<out T> {
    @Serializable
    data class Success<out T: Any>(@Serializable val data: T): Result<T>()
    @Serializable
    data class Failure(val message: String): Result<Nothing>()
}

/**
 * Functoriality
 */
@MathDsl
infix fun <S: Any,T: Any> Result<S>.map(f: (S)->T): Result<T> =
    when(this) {
        is Result.Success<S> -> try{
            Result.Success(f(data))
        } catch (e:Throwable) {
            Result.Failure(e.message?: "No message provided; source ${e::class}")
        }
        is Result.Failure -> Result.Failure(message)
    }

/**
 * Monad Return
 */
@Suppress("FunctionName")
@MathDsl
fun <T : Any> Result.Companion.Return(data: T): Result<T> = Result.Success(data)

operator fun <T : Any> Result.Companion.invoke(data: T): Result<T> = Result.Success(data)


/**
 * Monad multiplication
 */
@MathDsl
fun <T: Any> Result<Result<T>>.multiply(): Result<T> = when(this) {
    is Result.Success -> data
    is Result.Failure -> this
}

/**
 * Monad bind
 */
@MathDsl
infix fun <S:Any, T:Any> Result<S>.bind(f: (S) -> Result<T>): Result<T> = (this map f).multiply()

/**
 * Applicative
 */
@MathDsl
fun <S:Any, T:Any> Result<(S)->T>.apply(): (Result<S>)->Result<T> = {
    rS -> this bind {f -> rS map f}
}

/**
 * Helper
 */
@MathDsl
infix fun <S, T> ((S)->T).on(value: S):T = this(value)

