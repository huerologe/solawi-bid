package org.solyton.solawi.bid.application.ui.style.form

import org.jetbrains.compose.web.css.*

val desktopFormPageStyle: StyleScope.()->Unit by lazy { {
    width(80.percent)
    marginLeft(10.percent)
} }

val desktopFormStyle: StyleScope.()->Unit by lazy { {
    width(50.percent)
    padding(10.px)
    borderRadius(8.px)

    //shadow(Color.black, offsetX = 4.px, offsetY = 4.px, blurRadius = 10.px)
} }

val desktopFieldStyle: StyleScope.()->Unit by lazy {{
    width(100.percent)
    display(DisplayStyle.Flex)
    flexDirection(FlexDirection.Column)
    alignItems(AlignItems.Center)
    justifyContent(JustifyContent.Center)
}}

val desktopFormLabelStyle: StyleScope.()->Unit by lazy {{
    marginTop(5.px)
    width(100.percent)
}}

val desktopFormControlBarStyle: StyleScope.()->Unit by lazy { {
    marginTop(10.px)
    width(90.percent)
    display(DisplayStyle.Flex)
    flexDirection(FlexDirection.Row)
    alignItems(AlignItems.FlexEnd)
    //justifyContent(JustifyContent.Right)
} }

val desktopTextInputStyle: StyleScope.()->Unit by lazy {{
    marginTop(5.px)
    width(100.percent)
}}

val desktopNumberInputStyle: StyleScope.()->Unit by lazy {{
    marginTop(5.px)
    width(100.percent)
}}

val desktopDateInputStyle: StyleScope.()->Unit by lazy {{
    backgroundColor(Color.white)
}}
