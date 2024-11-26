package org.evoleq.language

interface Language {
    val locale: String
}



sealed class Lang(open val key: String) {
    data class Variable(
        override val key: String,
        val value: String
    ) : Lang(key)
    data class Block(
        override val key: String,
        val value: List<Lang>
    ) : Lang(key)
}

fun Lang?.find(name: String): Lang? = when(this) {
    null -> null
    is Lang.Variable -> this
    is Lang.Block -> value.find { this.key == name }
}

@I18N
operator fun Lang.get(path: String): String = with(Segment().run(path)) {
    when(this@get) {
        is Var -> this@get.value
        is Block -> with(this@get.value.find { it.key.equals( result!! ) }) {
            when(this)  {
                null -> throw Exception("There is no Element in block '${this@get.key}' with key = '$result'")
                else -> this[rest]
            }
        }
    }
}

@I18N
infix fun String.ofComponent(component: Block): Block = component.component(this)

@I18N
infix fun String.of(component: Block): Block = component.component(this)

@I18N
fun Block.component(path: String): Block = with(Segment().run(path)) {
    with(this@component.value.find { it.key.equals( result!! ) }) {
        when(this)  {
            null, is Var -> throw Exception("There is no block in block '${this@component.key}' with key = '$result'")
            is Block -> when(rest == ""){
                true -> this
                false -> this.component(rest)
            }
            else -> TODO("No idea during refactoring. But I guess it should not happen that we get here")
        }
    }
}

/**
 * Merge on lang to another
 * The right side will override the left side
 *
 */
fun Lang.merge(other: Lang): Lang {


    if(key == other.key) {
        if(this is Lang.Variable) {
            return other
        } else if (other is Lang.Variable){
            return merge(listOf(other))
        }
        require(other is Block)
        return merge(other.value)
    } else {
        return other
    }
}

/**
 * Merge a list of langs to the children of a block
 */
fun Lang.merge(langs: List<Lang>): Lang {
    if(langs.isEmpty()) return this
    if(this is Lang.Variable) throw Exception("cannot merge into variable")
    require(this is Block)
    val first = langs.first()
    val tail = langs.drop(1)

    val found = find(first.key)
    if(found == null) {
        return Block(key, listOf(*value.toTypedArray(),first)).merge(tail)
    } else {
        require(found is Block)
        val x = found.merge((first as Block).value)
        return Block(key, found.value.filter { it.key != first.key }.toMutableList().let{it.add(0,x); it})
    }
}