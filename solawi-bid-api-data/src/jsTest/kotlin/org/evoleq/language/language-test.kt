package org.evoleq.language

import kotlin.test.Test
import kotlin.test.assertEquals

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

}