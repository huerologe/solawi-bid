package org.solyton.solawi.bid.application.ui.style.form

import org.jetbrains.compose.web.css.*

val mobileFormPageStyle: StyleScope.()->Unit by lazy { {
    width(94.percent)
    marginLeft(3.percent)
} }

val mobileFormStyle: StyleScope.()->Unit by lazy {{
    width(100.percent)
    padding(20.px)
    borderRadius(16.px)
}}

val mobileFieldStyle: StyleScope.()->Unit by lazy {{
    width(100.percent)
    display(DisplayStyle.Flex)
    flexDirection(FlexDirection.Column)
    alignItems(AlignItems.Center)
    justifyContent(JustifyContent.Center)
}}

val mobileFormLabelStyle: StyleScope.()->Unit by lazy { {
    marginTop(10.px)
    width(100.percent)
} }

val mobileFormControlBarStyle: StyleScope.()->Unit by lazy { {
    marginTop(20.px)
    width(90.percent)
    display(DisplayStyle.Flex)
    flexDirection(FlexDirection.Row)
    alignItems(AlignItems.FlexEnd)
    //justifyContent(JustifyContent.Right)
} }

val mobileTextInputStyle: StyleScope.()->Unit by lazy {{
    marginTop(10.px)
    width(100.percent)
}}

val mobileNumberInputStyle: StyleScope.()->Unit by lazy {{
    marginTop(10.px)
    width(100.percent)
}}

val mobileDateInputStyle: StyleScope.()->Unit by lazy {{
    backgroundColor(Color.white)
}}
