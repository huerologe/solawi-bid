package org.evoleq.ktorx.result

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.solyton.solawi.bid.module.bid.data.api.Bid
import kotlin.test.Test
import kotlin.test.assertEquals


class ResultSerializationTests {

    // This test arose during tdd -> keep it
    @Test
    fun testSerializationInGeneral() {
        val user = Bid(

            "test-user",
            "", 2.0
        )

        val serialized = Json.encodeToString(user)

        val deserialized = Json.decodeFromString<Bid>(serialized)

        assertEquals(user, deserialized)
    }

    // This is exaggerated, but we keep it as an example
    @Test

    fun serialize() {
       // val (clazz, serializer, data) = input
        val user = Bid(

            "test-user",
            "", 2.0
        )
        serializers[Bid::class] = Bid.serializer()

        val success = Result.Success(user)

        val serialized = Json.encodeToString(success)
        val deserialized = Json.decodeFromString<Result.Success<Bid>>(serialized)

        assertEquals(success, deserialized)
    }


}


