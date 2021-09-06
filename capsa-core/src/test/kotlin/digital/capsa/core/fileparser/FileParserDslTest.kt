package digital.capsa.core.fileparser

import java.io.BufferedReader
import java.io.StringReader
import java.time.LocalDate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("unit")
class FileParserDslTest {

    data class Header(val string1: String?, val int1: Int, val date1: LocalDate?)

    data class Item(val string1: String?, val string2: String, val int1: Int?, val date1: LocalDate?)

    @Test
    fun `positive scenario`() {
        val parser = parser(
            BufferedReader(
                StringReader(
                    """
                    aaa 23 2021-03-09
                    bbb cc 23 2021-04-19
                    d e f1 34 2021-05-22
                    """.trimIndent()
                )
            )
        ) {
            header(17) {
                Header(
                    string1 = field(0, 4, "field1"),
                    int1 = mandatoryField(4, 7, "field2"),
                    date1 = field(7, 17, "field3")
                )
            }
            line {
                Item(
                    string1 = field(0, 4, "field1"),
                    string2 = mandatoryField(4, 7, "field2"),
                    int1 = field(7, 10, "field3"),
                    date1 = field(10, 20, "field4")
                )
            }
        }
        val header = parser.getRecords()[0].value as Header
        Assertions.assertEquals("aaa", header.string1)
        Assertions.assertEquals(23, header.int1)
        Assertions.assertEquals(LocalDate.parse("2021-03-09"), header.date1)

        var item = parser.getRecords()[1].value as Item
        Assertions.assertEquals("bbb", item.string1)
        Assertions.assertEquals("cc", item.string2)
        Assertions.assertEquals(23, item.int1)
        Assertions.assertEquals(LocalDate.parse("2021-04-19"), item.date1)

        item = parser.getRecords()[2].value as Item
        Assertions.assertEquals("d e", item.string1)
        Assertions.assertEquals("f1", item.string2)
        Assertions.assertEquals(34, item.int1)
        Assertions.assertEquals(LocalDate.parse("2021-05-22"), item.date1)
    }

    @Test
    fun `empty file`() {
        val parser = parser(
            BufferedReader(
                StringReader(
                    ""
                )
            )
        ) {
            header {
                Header(
                    string1 = field(0, 4, "field1"),
                    int1 = mandatoryField(4, 7, "field2"),
                    date1 = field(7, 17, "field3")
                )
            }
            line {
                Item(
                    string1 = field(0, 4, "field1"),
                    string2 = mandatoryField(4, 7, "field2"),
                    int1 = field(7, 10, "field3"),
                    date1 = field(10, 20, "field4")
                )
            }
        }
        Assertions.assertEquals(0, parser.getRecords().size)
    }

    @Test
    fun `line length is wrong`() {
        val parser = parser(
            BufferedReader(
                StringReader(
                    """
                    aaa 23 2021-03-09
                    bbb cc 23 2021-04-19
                    d e f1 34 2021-05-22
                    """.trimIndent()
                )
            )
        ) {
            header(18) {
                Header(
                    string1 = field(0, 4, "field1"),
                    int1 = mandatoryField(4, 7, "field2"),
                    date1 = field(7, 17, "field3")
                )
            }
            line(22) {
                Item(
                    string1 = field(0, 4, "field1"),
                    string2 = mandatoryField(4, 7, "field2"),
                    int1 = field(7, 10, "field3"),
                    date1 = field(10, 20, "field4")
                )
            }
        }
        Assertions.assertEquals("Line length should be 18 but was 17", parser.getRecords()[0].error?.message)
        Assertions.assertEquals("Line length should be 22 but was 20", parser.getRecords()[1].error?.message)
    }

}


