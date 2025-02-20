package org.solyton.solawi.bid.application.ui.style.form

import org.jetbrains.compose.web.css.*

const val mobileFontSizeFactor = 1.6

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
    marginTop(20.px)
    width(100.percent)
} }

val mobileFormControlBarStyle: StyleScope.()->Unit by lazy { {
    fontSize(mobileFontSizeFactor.em)
    marginTop(20.px)
    width(100.percent)
    display(DisplayStyle.Flex)
    flexDirection(FlexDirection.Column)
    //height(80.px)
    //alignItems(AlignItems.Center)
    //justifyContent(JustifyContent.Center)
} }

val mobileTextInputStyle: StyleScope.()->Unit by lazy {{
    fontSize(mobileFontSizeFactor.em)
    marginTop(20.px)
    width(100.percent)
}}

val mobileNumberInputStyle: StyleScope.()->Unit by lazy {{
    fontSize(mobileFontSizeFactor.em)
    marginTop(20.px)
    width(100.percent)
}}

val mobileDateInputStyle: StyleScope.()->Unit by lazy {{
    fontSize(mobileFontSizeFactor.em)
    backgroundColor(Color.white)
}}

val mobileFormButtonStyle: StyleScope.()->Unit by lazy {{
    property("font-size", "${mobileFontSizeFactor.em} !important" )
    //fontSize(mobileFontSizeFactor.em)
    marginTop(20.px)
    padding(10.px)
    width(100.percent)
    //height(100.percent)
    //minHeight(50.px)
}}
