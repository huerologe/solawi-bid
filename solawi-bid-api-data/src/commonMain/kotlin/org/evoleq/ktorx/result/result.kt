package org.evoleq.ktorx.result

import kotlinx.serialization.Serializable
import org.evoleq.math.MathDsl


@Serializable(with = ResultSerializer::class)
sealed class Result<out T : Any>{
    @Serializable
    data class Success<out T: Any>(@Serializable val data: T): Result<T>()
    @Serializable
    sealed class Failure: Result<Nothing>() {
        @Serializable
        data class Message( val value: String): Failure()
        data class Exception(val value: Throwable): Failure()
    }
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
            Result.Failure.Exception(e)//.message?: "No message provided; source ${e::class}")
        }
        is Result.Failure -> when(this){
            is Result.Failure.Message -> Result.Failure.Message(value)
            is Result.Failure.Exception -> Result.Failure.Exception(value)
        }
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

@MathDsl
fun Result<(Unit)->Unit>.dispatch(): Result<Unit> = map { it(Unit) }

/**
 * Helper
 */
@MathDsl
infix fun <S, T> ((S)->T).on(value: S):T = this(value)

