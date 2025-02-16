package org.solyton.solawi.bid.application.data.device

import kotlinx.browser.window

sealed class DeviceType(open val minWidth: Int,open val maxWidth: Int, open val order: Int) {
    data object Empty: DeviceType(0,0, 0)
    data object Mobile : DeviceType(0,600, 1)
    data object Tablet : DeviceType(601,768, 2)
    data object Laptop : DeviceType(769, 1280, 3)
    data object Desktop: DeviceType(1281, 1440, 5)

    data object Huge : DeviceType(1441, Int.MAX_VALUE, Int.MAX_VALUE)
    // ...

    companion object {
        fun from(width: Double) = when{
            width < Mobile.maxWidth -> Mobile
            width >= Mobile.maxWidth && getDeviceType() == "Mobile" -> Mobile
            width >= Tablet.minWidth && width < Tablet.maxWidth -> Tablet
            width >= Tablet.maxWidth && getDeviceType() == "Tablet" -> Tablet
            width >= Laptop.minWidth && width < Laptop.maxWidth -> Laptop
            width >= Desktop.minWidth && width < Desktop.maxWidth -> Desktop
            else -> Huge
        }
    }
}

fun getDeviceType(): String {
    val width = window.innerWidth
    val pixelRatio = window.devicePixelRatio
    val isTouchDevice = js("('ontouchstart' in window)") as Boolean

    return when {
        isTouchDevice && pixelRatio > 1 && width <= 600 -> "Mobile"
        isTouchDevice && pixelRatio > 1 && width <= 1024 -> "Tablet"
        else -> "Desktop"
    }
}

operator fun DeviceType.compareTo(other: DeviceType) = order compareTo other.order