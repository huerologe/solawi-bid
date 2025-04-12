package org.solyton.solawi.bid.application.data.transform.user

import org.evoleq.optics.lens.Lens
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.module.user.data.Application as UseModule

val userIso: Lens<Application, UseModule> = Lens(
    get = {whole -> UseModule(
        whole.userData,
        whole.i18N,
        whole.managedUsers,
        whole.availablePermissions
    )},
    set = {part -> {whole -> whole.copy(
        userData = part.user,
        i18N = part.i18n,
        managedUsers = part.managedUsers,
        availablePermissions = part.availablePermissions
    )}}
)