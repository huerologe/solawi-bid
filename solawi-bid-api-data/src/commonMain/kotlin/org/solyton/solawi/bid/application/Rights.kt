package org.solyton.solawi.bid.application

object Right{
    val create = Create
    val read = Read
    val update = Update
    val delete = Delete


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
