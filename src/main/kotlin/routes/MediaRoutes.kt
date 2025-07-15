package com.kis.routes

import com.kis.models.UrlRequest
import com.kis.models.VideoUrlResponse
import com.kis.service.ExtractService
import com.kis.services.RefererService
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.mediaRoutes(extractService: ExtractService, refererService: RefererService) {
    get("/") {
        call.respondText("Hello, Inspectra Media Server!")
    }

    post("/extract") {
        val req = call.receive<UrlRequest>()
        val result = extractService.extractVideoUrl(req.url)

        if (result != null) {
            call.respond(VideoUrlResponse(result))
        } else {
            call.respond(mapOf("error" to "Failed to extract video URL"))
        }
    }

    post("/referer") {
        val req = call.receive<UrlRequest>()
        val result = refererService.getReferer(req.url)

        if (result != null) {
            call.respond(VideoUrlResponse(result))
        } else {
            call.respond(mapOf("error" to "Failed to get URL from Referer header"))
        }
    }
}
