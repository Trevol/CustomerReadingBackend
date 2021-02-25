package com.tavrida.utils

fun String.log() = println(this)

fun String.log(prefix: Any) = "$prefix: $this".log()

fun Any.log() = this.toString().log()

fun Any.log(prefix: Any) = this.toString().log(prefix)
