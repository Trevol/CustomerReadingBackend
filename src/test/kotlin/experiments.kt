package test.kotlin

import com.tavrida.utils.log
import kotlinx.html.HTML
import kotlinx.html.dom.document
import kotlinx.html.*
import kotlinx.html.dom.*
import kotlinx.html.stream.appendHTML
import kotlin.random.Random

//Table builder!!!!

fun main() {
    data class TestItem(val id: Int, val value: Long)

    val items = listOf(
        TestItem(1, 1111),
        TestItem(2, 2222),
        TestItem(3, 3333)
    )
}