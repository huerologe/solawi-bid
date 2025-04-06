package org.solyton.solawi.bid.module.permission

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.evoleq.exposedx.migrations.Migration
import org.evoleq.exposedx.migrations.runOn
import org.evoleq.exposedx.test.Config
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.junit.jupiter.api.Test
import org.solyton.solawi.bid.DbFunctional
import org.solyton.solawi.bid.application.permission.Context
import org.solyton.solawi.bid.application.permission.Value
import org.solyton.solawi.bid.module.db.migrations.Migration1730143239225
import org.solyton.solawi.bid.module.db.migrations.Migration1743235367945
import org.solyton.solawi.bid.module.db.migrations.Migration1743786680319
import org.solyton.solawi.bid.module.db.repository.getChildren
import org.solyton.solawi.bid.module.db.repository.parent
import org.solyton.solawi.bid.module.db.schema.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class MigrationTests {
    val neededTables = arrayOf (
        Roles,
        Rights,
        Contexts,
        RoleRightContexts
    )

    @DbFunctional@Test fun contextMigrationTest() = runBlocking {
        val database = Database.connect(
            url = Config.H2NoClose.url,
            driver = Config.H2.driver,
            user = Config.H2.user,
            password = Config.H2.password
        )
        val migrations = arrayListOf<Database.()-> Migration>(
            { Migration1730143239225(database) },
            { Migration1743235367945(database) },
            { Migration1743786680319(database) }
        )

        val r = suspendedTransactionAsync {
            SchemaUtils.drop(*neededTables)
            migrations.runOn(database)
         }.await()
        delay(1000)
        val s = suspendedTransactionAsync {
            // Application Context
            // Exists:  APPLICATION, ORGANIZATION
            // Deleted: APPLICATION/ORGANIZATION
            val applicationContext =
                ContextEntity.find { ContextsTable.name eq Context.Application.value }.firstOrNull()
            assertNotNull(applicationContext)

            val oldApplicationOrganizationContext =
                ContextEntity.find { ContextsTable.name eq Context.Application.Organization.value }.firstOrNull()
            assertNull(oldApplicationOrganizationContext)

            val applicationOrganizationContext = ContextEntity.find {
                ContextsTable.name eq Value.ORGANIZATION
                ContextsTable.rootId eq applicationContext.id
            }.firstOrNull()
            assertNotNull(applicationOrganizationContext)

            assertEquals(applicationContext, applicationOrganizationContext.parent())

            // Organization Context
            // Exists:  ORGANIZATION, MANAGEMENT
            // Deleted: ORGANIZATION/MANAGEMENT
            val oldOrganizationManagementContext = ContextEntity.find{
                ContextsTable.name eq Context.Organization.Management.value
            }.firstOrNull()
            assertNull(oldOrganizationManagementContext)
            val organizationContext = ContextEntity.find{
                ContextsTable.name eq Context.Organization.value and
                (ContextsTable.rootId eq null)
            }.firstOrNull()
            assertNotNull(organizationContext)
            val managementContext = ContextEntity.find{
                ContextsTable.name eq Value.MANAGEMENT and
                (ContextsTable.rootId eq organizationContext.id)
            }.firstOrNull()
            assertNotNull(managementContext)

            assertEquals(organizationContext, managementContext.parent())

            // Auction Context
            // Exists:  AUCTION, MANAGEMENT
            // Deleted: AUCTION/MANAGEMENT
            val oldAuctionManagementContext = ContextEntity.find { ContextsTable.name eq Context.Auction.Management.value }.firstOrNull()
            assertNull(oldAuctionManagementContext)
            val auctionContext = ContextEntity.find { ContextsTable.name eq Context.Auction.value }.firstOrNull()
            assertNotNull(auctionContext)
            val auctionManagementContext = ContextEntity.find {
                ContextsTable.name eq Value.MANAGEMENT and
                (ContextsTable.rootId eq auctionContext.id)

            }.firstOrNull()
            assertNotNull(auctionManagementContext)

            assertEquals(auctionContext, auctionManagementContext.parent())

            // Overall Assertion
            assertEquals(6, ContextEntity.all().count())
        }.await()
    }
}