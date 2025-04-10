package org.solyton.solawi.bid.module.db.repository

import org.jetbrains.exposed.sql.insert
import org.solyton.solawi.bid.module.db.schema.ContextEntity
import org.solyton.solawi.bid.module.db.schema.RightEntity
import org.solyton.solawi.bid.module.db.schema.RoleEntity
import org.solyton.solawi.bid.module.db.schema.RoleRightContexts

fun grant(
    context: ContextEntity,
    role: RoleEntity,
    vararg rights: RightEntity
) {
    rights.forEach { right ->
        RoleRightContexts.insert {
            it[contextId] = context.id
            it[roleId] = role.id
            it[rightId] = right.id
        }
    }
}

data class RoleInContext(
    val role: RoleEntity, val context: ContextEntity
)

infix fun RoleEntity.of(context: ContextEntity): RoleInContext = RoleInContext(this, context)

fun RoleInContext.grant(vararg rights: RightEntity) = org.solyton.solawi.bid.module.db.repository.grant(
    context, role, *rights
)