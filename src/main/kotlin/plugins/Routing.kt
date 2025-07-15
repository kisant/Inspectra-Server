package com.kis.plugins

import com.kis.routes.mediaRoutes
import com.kis.service.ExtractService
import com.kis.services.RefererService
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject


fun Application.configureRouting() {
    val extractService by inject<ExtractService>()
    val refererService by inject<RefererService>()
    routing {
        mediaRoutes(extractService, refererService)
    }
}