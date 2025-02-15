package org.evoleq.language

import org.evoleq.math.Reader

interface LangComponent {
    val path: String
}

val component: (LangComponent)->Reader<Lang, Lang.Block> = { c -> Reader { lang -> (lang as Lang.Block).component(c.path) } }

val subComp: (String)->Reader<Lang, Lang.Block> = {c -> Reader { lang -> (lang as Lang.Block).component(c) }}
