package org.solyton.solawi.bid.application.ui.style.form

import org.jetbrains.compose.web.css.*
import org.solyton.solawi.bid.application.data.device.DeviceType
import org.solyton.solawi.bid.application.data.device.compareTo


fun formPageStyle(device: DeviceType): StyleScope.()->Unit = {
    display(DisplayStyle.Flex)
    flexDirection(FlexDirection.Column)
    alignItems(AlignItems.Center)
    justifyContent(JustifyContent.Center)
    backgroundColor(Color.white)
    height(100.vh) // Full viewport height
    when{
        device > DeviceType.Tablet -> mobileFormPageStyle()
        else -> desktopFormPageStyle()
    }
}

fun fieldStyle(device: DeviceType): StyleScope.()->Unit = {
    width(100.percent)
    display(DisplayStyle.Flex)
    flexDirection(FlexDirection.Column)
    alignItems(AlignItems.Center)
    justifyContent(JustifyContent.Center)
}

fun formStyle(device: DeviceType): StyleScope.()->Unit = {
    display(DisplayStyle.Flex)
    flexDirection(FlexDirection.Column)
    backgroundColor(Color.whitesmoke)
    when{
        device > DeviceType.Tablet -> desktopFormStyle()
        else -> mobileFormStyle()
    }
}

fun formLabelStyle(device: DeviceType): StyleScope.()->Unit = when {
    device > DeviceType.Tablet -> desktopFormLabelStyle
    else -> mobileFormLabelStyle
}


fun textInputStyle(device: DeviceType): StyleScope.()->Unit = when {
    device > DeviceType.Tablet -> desktopTextInputStyle
    else -> mobileTextInputStyle
}

fun numberInputStyle(device: DeviceType): StyleScope.()->Unit = when {
    device > DeviceType.Tablet -> desktopNumberInputStyle
    else -> mobileNumberInputStyle
}

fun formControlBarStyle(device: DeviceType): StyleScope.()->Unit = when {
    device > DeviceType.Tablet -> desktopFormControlBarStyle
    else -> mobileFormControlBarStyle
}

fun dateInputStyle(device: DeviceType): StyleScope.()->Unit = when {
    device > DeviceType.Tablet -> desktopDateInputStyle
    else -> mobileDateInputStyle
}
