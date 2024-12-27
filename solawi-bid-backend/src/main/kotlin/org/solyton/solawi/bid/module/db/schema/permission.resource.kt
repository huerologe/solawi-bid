package org.solyton.solawi.bid.module.db.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

typealias ResourceEntity = Resource
typealias ResourcesEntity = Resources

object Resources : UUIDTable("resources") {
    val name = varchar("name", 255)
}

class Resource(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Resource>(Resources)

    var name by Resources.name
    var context by Context referencedOn Contexts.id
}