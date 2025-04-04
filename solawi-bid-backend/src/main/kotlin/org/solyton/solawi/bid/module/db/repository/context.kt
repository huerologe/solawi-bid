package org.solyton.solawi.bid.module.db.repository

import org.jetbrains.exposed.sql.*
import org.solyton.solawi.bid.module.db.schema.*
import java.util.*

fun ContextEntity.ancestors(): SizedIterable<ContextEntity> {
    if(root == null) {
        return emptySized()
    }
    require(root != null)
    val rootId = root!!.id
    val ancs = ContextEntity.find {
        (ContextsTable.rootId eq rootId or (ContextsTable.id eq rootId)) and (ContextsTable.left less this@ancestors.left) and (ContextsTable.right greater this@ancestors.right)
    }
    return ancs.orderBy(ContextsTable.left to org.jetbrains.exposed.sql.SortOrder.ASC)
}

fun ContextEntity.descendants(): SizedIterable<ContextEntity> = with(root?.id?:id) {
    val rootId = this
    ContextEntity.find {
        ContextsTable.rootId eq rootId and (ContextsTable.left greater this@descendants.left) and (ContextsTable.right less this@descendants.right)
    }.orderBy(ContextsTable.left to org.jetbrains.exposed.sql.SortOrder.ASC)
}

fun createRootContext(ContextName: String, user: UserEntity): ContextEntity {
    val ContextContext = ContextEntity.new {
        name = "Context/${ContextName.replace(" ", "_").uppercase()}"
    }
    val manager = RoleEntity.find { Roles.name eq "MANAGER" }.first()
    val read = RightEntity.find { Rights.name eq "READ" }.first()
    val write = RightEntity.find { Rights.name eq "UPDATE" }.first()

    RoleRightContexts.insert {
        it[roleId] = manager.id
        it[rightId] = read.id
        it[contextId] = ContextContext.id
    }

    RoleRightContexts.insert {
        it[roleId] = manager.id
        it[rightId] = write.id
        it[contextId] = ContextContext.id
    }

    return ContextEntity.new {
        name = ContextName
    }
}


fun ContextEntity.createChild(name: String): ContextEntity {
    val ancestors = ancestors()
    println(ancestors.map { it.name })
    val maxRight = root?.right?:1
    val siblings = ContextEntity.find {
        ContextsTable.rootId eq root?.id and (ContextsTable.left greater right) and (ContextsTable.right less maxRight)
    }
    val oldRight = right
    right += 2
    ancestors.forEach {
        it.right += 2
    }
    siblings.forEach {
        it.left += 2
        it.right += 2
    }
    val rootOrg = when{
        level == 0 -> this
        else -> root!!
    }
    val childLevel = level +1
    return ContextEntity.new {
        root = rootOrg
        this.name = name
        left = oldRight
        right = oldRight + 1
        level = childLevel
    }
}

fun ContextEntity.removeChild(childId: UUID) : ContextEntity {
    val child = ContextEntity.findById(childId)?: throw org.solyton.solawi.bid.module.db.ContextException.NoSuchChildContext(childId)
    child.remove()
    return this
}


fun ContextEntity.remove(): Unit {

    val maxRight = root?.right?:1
    val diff = right - left +1

    val descendants = descendants()
    val ancestors = ancestors()
    val siblings = ContextEntity.find {
        ContextsTable.rootId eq root?.id and (ContextsTable.left greater left) and (ContextsTable.right less maxRight)
    }

    ancestors.forEach {
        it.right -= diff
    }
    siblings.forEach {
        it.right -= diff
        it.left -= diff
    }
    descendants.forEach { it.delete() }
    delete()
}

fun ContextEntity.getChildren(): SizedIterable<ContextEntity> {
    val rootId = root?.id?:id
    val childLevel = level + 1
    return ContextEntity.find {
        ContextsTable.rootId eq rootId and (ContextsTable.level eq childLevel) and (ContextsTable.left greater left) and (ContextsTable.right less right)
    }
}

fun ContextEntity.parent(): ContextEntity? = when {
    root == null -> null
    else -> ContextEntity.find {
        (ContextsTable.rootId eq root!!.id or (ContextsTable.id eq root!!.id)) and
        (ContextsTable.level eq (level -1)) and
        (ContextsTable.left less left) and
        (ContextsTable.right greater right)
    }.first()
}

fun ContextEntity.pathAsList(): List<ContextEntity> = when {
    root == null -> listOf(this)
    else -> listOf(
        *ancestors().filter {
            it.left <= left && it.right >= right
        }.sortedBy { it.level }.toTypedArray(),
        this
    )
}

fun ContextEntity.path(): String = pathAsList().join()

fun List<ContextEntity>.join(): String = joinToString("/") { it.name }
