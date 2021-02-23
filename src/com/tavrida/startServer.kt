package com.tavrida

import com.tavrida.server.ServerApplication


fun main() {
    ServerApplication(8080, wait = true).start()
}