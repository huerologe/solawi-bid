package org.solyton.solawi.bid.application.permission

import org.solyton.solawi.bid.shared.ValueWithDescription

// typealias Right = ValueWithDescription

object Right{
    val create = Create
    val read = Read
    val update = Update
    val delete = Delete

    val readRightRoleContexts = object : ValueWithDescription {
        override val value: String = "READ_ROLE_RIGHT_CONTEXTS"
        override val description: String = "Read role-right-contexts of users"
    }

    val readRightsAndRoles = object : ValueWithDescription {
        override val value: String = "READ_ROLES_AND_RIGHTS"
        override val description: String = "Read roles and rights of users"
    }

    object Organization {
        val create = object : ValueWithDescription {
            override val value: String= "CREATE_ORGANIZATION"
            override val description: String = "Create organization in a context"
        }
        val read = object : ValueWithDescription {
            override val value: String= "READ_ORGANIZATION"
            override val description: String = "Read organization in a context"
        }
        val update = object : ValueWithDescription {
            override val value: String= "UPDATE_ORGANIZATION"
            override val description: String = "Update organization in a context"
        }
        val delete = object : ValueWithDescription {
            override val value: String= "DELETE_ORGANIZATION"
            override val description: String = "Delete organization in a context"
        }
    }

    object Application {
        object Users {
            val manage = object : ValueWithDescription {
                override val value: String = "MANAGE_USERS"
                override val description: String = "Manage Users"
            }
        }

        object Subscriptions {
            val readAvailableApplications = object : ValueWithDescription {
                override val value: String = "READ_AVAILABLE_APPLICATIONS"
                override val description: String = "Read available application subscriptions"
            }
            val subscribeApplications = object : ValueWithDescription {
                override val value: String = "SUBSCRIBE_APPLICATIONS"
                override val description: String = "available applications"
            }
            val unsubscribeApplications = object : ValueWithDescription {
                override val value: String = "UNSUBSCRIBE_APPLICATIONS"
                override val description: String = "unsubscribe applications"
            }
        }
    }

    object BidRound {
        val manage = object : ValueWithDescription {
            override val value: String= "MANAGE_BID_ROUND"
            override val description: String = "Manage Bid round"
        }

    }
    object Auction {
        val manage = object : ValueWithDescription {
            override val value: String= "MANAGE_AUCTION"
            override val description: String = "Manage Auction"
        }
    }
}

object Create : ValueWithDescription {
    override val value  = "CREATE"
    override val description: String = "General right to create something in a context"
}

object Read : ValueWithDescription {
    override val value  = "READ"
    override val description: String = "General right to read something in a context"
}

object Update : ValueWithDescription {
    override val value  = "UPDATE"
    override val description: String = "General right to update something in a context"
}

object Delete : ValueWithDescription {
    override val value  = "DELETE"
    override val description: String = "General right to delete something in a context"
}
