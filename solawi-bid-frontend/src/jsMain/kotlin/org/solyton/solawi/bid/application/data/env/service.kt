package org.solyton.solawi.bid.application.data.env


val env:dynamic by lazy { js("PROCESS_ENV") }


fun getEnv(): Environment = Environment(
    type = env.ENVIRONMENT.unsafeCast<String>(),
    frontendUrl = env.FRONTEND_URL.unsafeCast<String>(),
    frontendPort = env.FRONTEND_PORT.unsafeCast<String>().toInt(),
    backendUrl = env.BACKEND_URL.unsafeCast<String>(),
    backendPort = env.BACKEND_PORT.unsafeCast<String>().toInt(),
)