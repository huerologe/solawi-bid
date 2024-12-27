package org.solyton.solawi.bid.module.db.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

typealias OrganizationsTable = Organisations
typealias OrganizationEntity = Organization

object Organisations : UUIDTable("organizations") {
    val name = varchar("name", 255).uniqueIndex()

    // sub organizations (tree left - right)
    val parentId = uuid("parent_id").nullable()
    val left = integer("left").default(0)
    val right = integer("right").default(1)

    // partner organizations (mapping table)

    // members

    // contact person

    // bank account ...


}

class Organization(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Organization>(Organisations)

    var name by Organisations.name


}