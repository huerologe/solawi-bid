package org.evoleq.csv

import org.solyton.solawi.bid.module.bid.data.api.BidderData
import java.io.File
import kotlin.test.Test

class TransformWeblingDataExportTest {
    /*

    @Test
    fun transformTest() {

        val parsed = parseCsv(sourceFile.readText())

        val keys = parsed.first().keys

        println("""
            |
            |keys = $keys
            |
        """.trimMargin())


        val searchData = parsed.filter{
            try{
                it["Anzahl Anteile"]!!.toInt()
                it["E-Mail*"]!!.isNotBlank() && it["Depot"]!!.isNotBlank() && !exclude.contains(it["E-Mail*"]!!)
            } catch (e:Exception){
                false
            }
        }.map{
            BidderData(
                it["Vorname*"]!!,
                it["Name*"]!!,
                it["E-Mail*"]!!,
                it["Anzahl Anteile"]!!.toInt(),
                try{it["Eieranteile je 15,86/Monat (61cent*6/Woche)"]!!.toInt()}catch(e:Exception){0},
                listOf(
                    it["E-Mail2"]!!,
                    it["E-Mail3"]!!,
                    it["E-Mail4"]!!,
                    it["E-Mail5"]!!,
                    it["E-Mail6"]!!,
                    it["E-Mail7"]!!,
                    it["E-Mail8"]!!,
                ).filter { it.isNotBlank() },
                listOf(
                    *listOf(it["Vorname (Mitanteilsnehmer*)"]!!).filter {jt -> jt.isNotBlank() }.toTypedArray(),
                    *listOf(it["Nachname (Mitanteilsnehmer*)"]!!).filter { jt ->jt.isNotBlank() }.toTypedArray()
                ),
            )
        }



        println("""
            |
            |searchData = ${searchData.first()}
            |
            """)

        val newCsv = """
            |Vorname;Nachname;Email;Anteile;Eier-Anteile;Emails;Data
            |${searchData.joinToString("\n") { 
                "${it.firstname};${it.lastname};${it.email};${it.numberOfShares};${it.numberOfEggShares};${it.relatedEmails.joinToString(",") { jt -> jt }};${it.relatedNames.joinToString(",") { jt -> jt }}"
        }}
        """.trimMargin()

        //println(newCsv)
        targetFile.writeText(newCsv)
       // console.log(keys)

    }
*/
}
