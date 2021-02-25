package com.tavrida.utils

fun <T, TKey> List<T>.toMap(key: (T) -> TKey) = map { key(it) to it }.toMap()
fun <T, TKey, TVal> List<T>.toMap(key: (T) -> TKey, value: (T) -> TVal) = map { key(it) to value(it) }.toMap()