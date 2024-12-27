package org.solyton.solawi.bid.module.db.schema

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.SizedIterable
import java.util.*

object UserProfiles : UUIDTable("user_profiles") {
    val userId = reference("user_id", Users)

    // Address etc
    // phone nr
    // street nr, city, country, postal code
    // Bank Account
    val bankAccountId = reference("bank_account_id", BankAccounts)// nullable default

    // shares
}

class UserProfile(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserProfile>(UserProfiles)

    var user by User referencedOn UserProfiles.userId

    var bankAccount by BankAccount referencedOn UserProfiles.bankAccountId

    val shares: SizedIterable<Share> by Share referrersOn Shares.userProfileId
}