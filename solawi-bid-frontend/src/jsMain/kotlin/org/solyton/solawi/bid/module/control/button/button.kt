package org.solyton.solawi.bid.module.control.button

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import org.evoleq.math.Source
import org.evoleq.math.emit
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.solyton.solawi.bid.application.data.device.DeviceType
import org.solyton.solawi.bid.application.ui.style.button.buttonStyle
import org.solyton.solawi.bid.application.ui.style.button.cancelButtonStyle
import org.solyton.solawi.bid.application.ui.style.button.submitButtonStyle

@Markup
@Composable
@Suppress("FunctionName")
fun SubmitButton(texts: Source<String>,deviceType: DeviceType, onClick: ()->Unit) = Button(
    attrs = {
        style {
            submitButtonStyle(deviceType)()
        }
        onClick {
            onClick()
        }
    }
) {
    Text(texts.emit())
}

@Markup
@Composable
@Suppress("FunctionName")
fun CancelButton(texts: Source<String>,deviceType: DeviceType, onClick: ()->Unit) = Button(
    attrs = {
        style {
            cancelButtonStyle(deviceType)()
        }
        onClick {
            onClick()
        }
    }
) {
    Text(texts.emit())
}

@Markup
@Composable
@Suppress("FunctionName")
fun StdButton(texts: Source<String>,deviceType: Source<DeviceType>, onClick: ()->Unit) =
    StdButton(texts, deviceType.emit(), onClick)

@Markup
@Composable
@Suppress("FunctionName")
fun StdButton(texts: Source<String>,deviceType: DeviceType, onClick: ()->Unit) = Button(
    attrs = {
        style {
            buttonStyle(deviceType)()
        }
        onClick {
            onClick()
        }
    }
) {
    Text(texts.emit())
}