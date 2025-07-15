package com.kis

import com.kis.plugins.configureExceptionHandler
import com.kis.plugins.configureInjectionModules
import com.kis.plugins.configureRouting
import com.kis.plugins.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain


fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureInjectionModules()
    configureSerialization()
    configureExceptionHandler()
    configureRouting()
}
