package com.kis.plugins

import com.kis.di.mediaModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureInjectionModules() {
    install(Koin) {
        slf4jLogger()
        modules(mediaModule)
    }
}