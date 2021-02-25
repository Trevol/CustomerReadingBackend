package test.kotlin

import com.tavrida.utils.log

fun main() {
    assert (mapOf(1 to 11, 2 to 22)[1] == 11)
    assert (mapOf(1 to 11, 2 to 22)[3] == null)
}