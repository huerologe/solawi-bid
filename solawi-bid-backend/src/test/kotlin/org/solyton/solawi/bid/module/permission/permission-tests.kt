package org.solyton.solawi.bid.module.permission

import org.evoleq.exposedx.test.runSimpleH2Test
import org.junit.jupiter.api.Test
import org.solyton.solawi.bid.DbFunctional
import org.solyton.solawi.bid.module.db.schema.UserEntity
import org.solyton.solawi.bid.module.permission.action.db.isGranted


class PermissionTests {
    @DbFunctional@Test
    fun testIsGranted() = runSimpleH2Test(

    ){
        val user = UserEntity.new {
            username = "x"
            password = "y"
        }
        val result = isGranted(user.id.value, "egal", "lesen")
    }
}