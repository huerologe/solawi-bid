package org.solyton.solawi.bid.module.db.migrations

import org.evoleq.exposedx.migrations.Migration
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Transaction
import org.solyton.solawi.bid.module.db.schema.*

/**
 * Autogenerated [Migration],
 * generated by the evoleq/exposedx migration gradle-plugin.
 * Alter with care!
 *
 * Generated at Fri Dec 27 22:18:25 CET 2024
 *
 * Description: Add tables to handle Shares, Fiscal stuff, certain User data and Organizations
 * - Shares,
 * - ShareTypes,
 * - BankAccounts,
 * - FiscalYears,
 * - DistributionPoints,
 * - Addresses,
 * - Organizations,
 * - UserOrganization
 */
class Migration1735334305003(
    override val database: Database
) : Migration {

    /**
     * Id of the migration, do not change!
     */
    override val id: Long
        get() = 1735334305003

    /**
     * Upwards migration
     */
    override suspend fun Transaction.up() {
        SchemaUtils.create(
            Shares,
            ShareTypes,
            BankAccounts,
            FiscalYears,
            DistributionPoints,
            Addresses,
            Organisations,
            UserOrganization
        )
    }

    /**
     * Downwards migration (inverse to the upward migration).
     * These migrations are not taken into account by now!
     */
    override suspend fun Database.down() {
        TODO("Not yet implemented")
    }
}
