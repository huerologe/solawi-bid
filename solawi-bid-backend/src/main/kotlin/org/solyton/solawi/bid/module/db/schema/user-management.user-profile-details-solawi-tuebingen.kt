package org.solyton.solawi.bid.module.db.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

/**
 * Obsoloere TODO(Remove)
 */

object UserProfileDetailsSolawiTuebingenTable : UUIDTable("user_profile_details_solawi_tuebingen") {
    val userProfileId = reference("user_profile_id", UserProfiles)

    val numberOfEggShares = integer("number_of_egg_shares").default(0)

    val achAuthEggs = bool("ach_auth_eggs").default(false)

    val numberOfVegetableShares = integer("number_of_vegetable_shares").default(0)

    val achAuthVegetable = bool("ach_auth_vegetables").default(false)


}

class UserProfileDetailsSolawiTuebingen(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserProfile>(UserProfiles)

    var userProfile by UserProfile referencedOn UserProfileDetailsSolawiTuebingenTable.userProfileId

    var numberOfEggShares by UserProfileDetailsSolawiTuebingenTable.numberOfEggShares
}