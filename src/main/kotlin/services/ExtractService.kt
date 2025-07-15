package com.kis.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.InputStreamReader

class ExtractService {
    private val logger = LoggerFactory.getLogger(ExtractService::class.java)

    suspend fun extractVideoUrl(targetUrl: String): String? = withContext(Dispatchers.IO) {
        try {
            val process = ProcessBuilder("yt-dlp", "-g", targetUrl)
                .redirectErrorStream(true)
                .start()

            val outputLines = BufferedReader(InputStreamReader(process.inputStream)).use { it.readLines() }
            val exitCode = process.waitFor()

            val videoUrl = outputLines.firstOrNull { it.startsWith("http") }

            if (exitCode == 0 && videoUrl != null) {
                videoUrl
            } else {
                if (exitCode != 0) {
                    logger.error("yt-dlp exited with code $exitCode. Output: $outputLines")
                }
                null
            }
        } catch (e: Exception) {
            logger.error("Exception in extractVideoUrl", e)
            null
        }
    }
}