package org.solyton.solawi.bid.module.permission

import org.evoleq.exposedx.test.runSimpleH2Test
import org.solyton.solawi.bid.DbFunctional
import org.solyton.solawi.bid.module.db.repository.createChild
import org.solyton.solawi.bid.module.db.repository.parent
import org.junit.jupiter.api.Test
import org.solyton.solawi.bid.module.db.repository.path
import org.solyton.solawi.bid.module.db.repository.pathAsList
import org.solyton.solawi.bid.module.db.schema.*
import kotlin.test.assertEquals

class ContextTests {

    val neededTables = arrayOf (
        Roles,
        Rights,
        Contexts,
        RoleRightContexts
    )

     @DbFunctional@Test fun getParentTest() = runSimpleH2Test(*neededTables){
         val root = ContextEntity.new {
             name = "Root"
         }

         val context1_1 = root.createChild("context1_1")
         val context1_2 = root.createChild("context1_2")
         val context1_1_1 = context1_1.createChild("context1_1_1")

         assertEquals(null, root.parent())
         assertEquals(root, context1_1.parent())
         assertEquals(root, context1_2.parent())
         assertEquals(context1_1, context1_1_1.parent())
     }

    @DbFunctional@Test fun computeContextPathTest() = runSimpleH2Test(*neededTables){
        val root = ContextEntity.new {
            name = "Root"
        }

        val context1_1 = root.createChild("context1_1")
        val context1_2 = root.createChild("context1_2")
        val context1_1_1 = context1_1.createChild("context1_1_1")

        assertEquals(
            listOf(root),
            root.pathAsList()
        )

        assertEquals(
            listOf(root, context1_1),
            context1_1.pathAsList()
        )

        assertEquals(
            listOf(root, context1_2),
            context1_2.pathAsList()
        )

        assertEquals(
            listOf(root,context1_1, context1_1_1),
            context1_1_1.pathAsList()
        )

        assertEquals(
            "${root.name}/${context1_1.name}",
            context1_1.path()
        )
        assertEquals(
            "${root.name}/${context1_2.name}",
            context1_2.path()
        )
        assertEquals(
            "${root.name}/${context1_1.name}/${context1_1_1.name}",
            context1_1_1.path()
        )
    }
}