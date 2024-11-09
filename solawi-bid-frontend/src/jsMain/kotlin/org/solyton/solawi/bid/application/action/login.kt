package org.solyton.solawi.bid.application.action

import lib.optics.lens.Lens
import lib.optics.lens.sX
import lib.optics.storage.Storage
import lib.optics.transform.times
import org.evoleq.ktorx.result.*
import org.evoleq.math.MathDsl
import org.evoleq.math.Reader
import org.evoleq.math.read
import org.evoleq.maths.state.*
import org.evoleq.maths.x
import lib.optics.lens.x as X
import org.solyton.solawi.bid.application.api.client
import org.solyton.solawi.bid.application.api.post
import org.solyton.solawi.bid.application.data.Application
import org.solyton.solawi.bid.application.data.userData
import org.solyton.solawi.bid.module.authentication.data.api.LoggedIn
import org.solyton.solawi.bid.module.authentication.data.api.Login
import org.solyton.solawi.bid.module.user.User

@MathDsl
@Suppress("FunctionName")
suspend fun LoginAction() = ReadLogin bind CallLogin bind DispatchLogin


data class LoginData(
    val username: String,
    val passwort: String,
    val backendUrl: String
)

val loginData: Lens<Application, LoginData> = Lens(
    get = {w -> LoginData(w.userData.username, w.userData.password, w.environment.backendUrl)},
    set = {l -> {w -> w.copy()}}
)

val loginDataReader: (Storage<Application>)->LoginData = {
    (it * loginData).read()
}

val CallLogin = KlStorageState<LoginData, Result<LoggedIn>> { login-> StorageState {
    storage -> login(login) x storage
} }

val ReadLogin = StorageState<LoginData> {
    storage: Storage<Application> ->
        (storage * loginData ).read() x storage
}

val DispatchLogin = KlStorageState<Result<LoggedIn>, Result<Unit>> {
    result -> StorageState { storage ->
        (Result(storage * userData * {
            l:LoggedIn -> {uD: User ->uD.copy(
                javaWebToken = l.accessToken,
                refreshToken = l.refreshToken
            ) } }
        )
        .apply() on result).dispatch() x storage
    }
}

suspend fun Application.login(login: Login): Result<LoggedIn> = with(
    client(loggedIn = false).post<Login, LoggedIn>("${environment.backendUrl}/login")
) {
    return this(login)
}