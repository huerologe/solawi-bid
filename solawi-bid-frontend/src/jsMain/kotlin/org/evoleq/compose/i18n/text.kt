package org.evoleq.compose.i18n

import androidx.compose.runtime.Composable
import org.evoleq.compose.Markup
import lib.language.Block
import lib.language.get
import org.jetbrains.compose.web.dom.Text as CText

@Markup
@Composable
@Suppress("FunctionName")
fun Block.Text(path: String) = CText(this[path])