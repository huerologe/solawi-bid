package org.solyton.solawi.bid.application.service

import org.solyton.solawi.bid.module.user.User

fun User.isLoggerIn(): Boolean = accessToken != ""  && refreshToken!= ""

fun User.isNotLoggerIn(): Boolean = !isLoggerIn()