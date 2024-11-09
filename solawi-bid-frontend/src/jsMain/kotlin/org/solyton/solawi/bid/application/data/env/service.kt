package org.solyton.solawi.bid.application.data.env

val env by lazy { js("PROCESS_ENV") }


fun getEnv(): Environment = Environment(
    env.ENVIRONMENT
)