package com.kis.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class RefererService {
    private val logger = LoggerFactory.getLogger(RefererService::class.java)

    @Serializable
    private data class YtDlpHeaders(
        @SerialName("http_headers") val httpHeaders: Map<String, String>? = null
    )

    suspend fun getReferer(targetUrl: String): String? = withContext(Dispatchers.IO) {
        try {
            val process = ProcessBuilder(
                "yt-dlp",
                "-j",
                targetUrl
            )
                .redirectErrorStream(true)
                .start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()
            if (exitCode != 0) {
                logger.error("yt-dlp exited with code $exitCode. Output: $output")
                return@withContext null
            }

            val json = Json { ignoreUnknownKeys = true }
            val ytDlpHeaders = json.decodeFromString<YtDlpHeaders>(output)
            val referer = ytDlpHeaders.httpHeaders?.get("Referer")
            if (referer == null) {
                logger.error("Referer not found in yt-dlp output: $output")
            }
            referer
        } catch (e: Exception) {
            logger.error("Exception in getReferer", e)
            null
        }
    }
}