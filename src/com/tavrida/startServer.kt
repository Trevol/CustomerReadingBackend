package com.tavrida

import com.tavrida.server.ServerApplication
import io.ktor.server.cio.*


fun main() {
    ServerApplication(8080, wait = true, factory = CIO).start()
}