package org.solyton.solawi.bid.application.ui.style.button

import org.jetbrains.compose.web.css.*
import org.solyton.solawi.bid.application.data.device.DeviceType
import org.solyton.solawi.bid.application.data.device.compareTo

fun buttonStyle(deviceType: DeviceType): StyleScope.()->Unit = {
    backgroundColor(Color.white)
    borderRadius(5.px)
    padding(4.px, 8.px)
    cursor("pointer")
}

fun submitButtonStyle(deviceType: DeviceType): StyleScope.()->Unit = {
    buttonStyle(deviceType)()
    backgroundColor(Color.seagreen)
    when{
        deviceType > DeviceType.Tablet -> submitButtonDesktopStyle()
        else -> submitButtonMobileStyle()
    }
}

fun cancelButtonStyle(deviceType: DeviceType): StyleScope.()->Unit = {
    buttonStyle(deviceType)()
    backgroundColor(Color.crimson)
}