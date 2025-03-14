package org.evoleq.language

import org.evoleq.parser.ParsingResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class LanguageTest {


    @Test fun merge1() {
        val base = texts {
            key = "base"
            variable {
                key = "1"
                value = "1"
            }
        }
        val new = texts {
            key = "base"
            variable {
                key = "2"
                value = "2"
            }
        }
        val merged = base.merge(new)
        val expected = texts {
            key = "base"
            variable {
                key = "1"
                value = "1"
            }
            variable {
                key = "2"
                value = "2"
            }
        }
        assertEquals(expected, merged)
    }

    @Test fun merge2() {
        val base = texts {
            key = "base"
            variable {
                key = "1"
                value = "1"
            }
        }
        val new = texts {
            key = "base"
            variable {
                key = "1"
                value = "2"
            }
        }
        val merged = base.merge(new)
        val expected = texts {
            key = "base"
            variable {
                key = "1"
                value = "2"
            }

        }
        assertEquals(expected, merged)
    }




    @Test fun merge5() {
        val langBase = texts{
            key = "base"
            block{
                key = "block"
                variable {
                    key = "1"
                    value = "1"
                }
            }
        }

        val newLang = texts{
            key = "base"
            block{
                key = "block"
                variable {
                    key = "2"
                    value = "2"
                }
            }
        }

        val merged = langBase.merge(newLang)

        val expected = texts{
            key = "base"
            block{
                key = "block"
                variable {
                    key = "1"
                    value = "1"
                }
                variable {
                    key = "2"
                    value = "2"
                }
            }
        }
        assertEquals(expected, merged)
    }
    @Test fun mergeComponents() {
        val c1 = """
            |de{
            |   module{
                |   component1{
                |       name: "c1"
                |   }
            |   }
            |}
        """.trimMargin()
        val c2 = """
            |de{
            |   module{
                |   component2{
                |       name: "c2"
                |   }
            |   }
            |}
        """.trimMargin()
        val b1Result = LanguageP().run(c1)
        assertTrue{b1Result.result != null}
        val b1 = b1Result.result!!
        val b2Result = LanguageP().run(c2)
        assertTrue{b2Result.result != null}
        val b2 = b2Result.result!!

        val merged = b1.merge(b2)

        println(merged)

        val components = (merged as Block).component(
            "module"
        )
        assertEquals(2, components.value.size)

        val comp1 = components.component("component1")
        val comp2 = components.component("component2")

        assertEquals("c1", comp1["name"])
        assertEquals("c2", comp2["name"])
    }
}