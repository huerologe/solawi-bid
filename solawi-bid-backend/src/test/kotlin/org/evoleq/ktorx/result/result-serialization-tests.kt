package org.evoleq.ktorx.result

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.solyton.solawi.bid.Unit
import org.solyton.solawi.bid.module.user.data.api.User
import java.util.*
import kotlin.reflect.KClass
import kotlin.test.assertEquals
class ResultSerializationTests {

    // This test arose during tdd -> keep it
    @Unit@Test
    fun testSerializationInGeneral() {
        val user = User(
            UUID.randomUUID(),
            "test-user",
            "test-password"
        )

        val serialized = Json.encodeToString(user)

        val deserialized = Json.decodeFromString<User>(serialized)

        assertEquals(user, deserialized)
    }

    // This is exaggerated, but we keep it as an example
    @Unit@ParameterizedTest
    @MethodSource("userInputProvider")
    fun serialize(input: TestData<User>) {
        val (clazz, serializer, data) = input

        serializers[clazz] = serializer
        serializers[Any::class] = serializer
        val success = Result.Success(data)

        val serialized = Json.encodeToString(success)
        val deserialized = Json.decodeFromString<Result.Success<User>>(serialized)

        assertEquals(success, deserialized)
    }

    companion object {
        @JvmStatic
        fun userInputProvider() = listOf(
            TestData(
                User::class,
                User.serializer(),
                User(
                    UUID.randomUUID(),
                    "test-user",
                    "test-password"
                )
            ),
            TestData(
                User::class,
                User.serializer(),
                User(
                    UUID.randomUUID(),
                    "test-user-2",
                    "test-password"
                )
            ),
            TestData(
                User::class,
                User.serializer(),
                User(
                    UUID.randomUUID(),
                    "test-user-3",
                    "test-password"
                )
            )

        )
    }



    data class TestData<out T: Any>(
        val clazz: KClass<out T>,
        val serializer: KSerializer<out T>,
        val data: T,
    )
}


