package com.ruskiikot.mentoria01.util

fun String?.startsAsHttpUrl(): Boolean {
    return this?.startsWith("http", ignoreCase = true) ?: false
}