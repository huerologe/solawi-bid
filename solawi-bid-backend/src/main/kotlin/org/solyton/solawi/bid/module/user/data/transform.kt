package org.solyton.solawi.bid.module.user.data

import org.solyton.solawi.bid.module.user.data.api.User
import org.solyton.solawi.bid.module.db.schema.User as UserEntity

fun UserEntity.toApiType(): User = User(
    id.value,
    username,
    password
)


