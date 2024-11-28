package org.solyton.solawi.bid.application.ui.style.form

import org.jetbrains.compose.web.css.*

val formPageStyle: StyleScope.()->Unit = {
    display(DisplayStyle.Flex)
    flexDirection(FlexDirection.Column)
    alignItems(AlignItems.Center)
    justifyContent(JustifyContent.Center)
    height(100.vh) // Full viewport height
    width(80.vw) // 80% Full viewport width
    backgroundColor(Color.white) //Color("#f7f7f7") Light background color
}


val fieldStyle: StyleScope.()->Unit by lazy {{
    width(100.percent)
    display(DisplayStyle.Flex)
    flexDirection(FlexDirection.Column)
    alignItems(AlignItems.Center)
    justifyContent(JustifyContent.Center)
}}


val formStyle: StyleScope.()->Unit by lazy {{
    display(DisplayStyle.Flex)
    flexDirection(FlexDirection.Column)
    width(50.percent)
    padding(10.px)
    borderRadius(8.px)
    backgroundColor(Color.whitesmoke)
    //shadow(Color.black, offsetX = 4.px, offsetY = 4.px, blurRadius = 10.px)

}}

val formLabelStyle: StyleScope.()->Unit by lazy {{
    marginTop(5.px)
    width(100.percent)
}}

val textInputStyle: StyleScope.()->Unit by lazy {{
    marginTop(5.px)
    width(100.percent)

}}

val formControlBarStyle: StyleScope.()->Unit by lazy { {
    marginTop(10.px)
    width(90.percent)
    display(DisplayStyle.Flex)
    flexDirection(FlexDirection.Row)
    alignItems(AlignItems.FlexEnd)
    //justifyContent(JustifyContent.Right)
} }

val dateInputStyle: StyleScope.()->Unit by lazy {{
    backgroundColor(Color.white)
}}
