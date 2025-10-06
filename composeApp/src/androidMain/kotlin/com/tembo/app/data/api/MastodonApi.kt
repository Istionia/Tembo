package com.tembo.app.data.api

import com.tembo.app.data.models.Status
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class MastodonApi(
    private val instanceUrl: String,
    private val accessToken: String
) {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.INFO
        }
    }

    suspend fun getHomeTimeline(limit: Int = 20): List<Status> {
        return client.get("$instanceUrl/api/v1/timelines/home") {
            parameter("limit", limit)
            bearerAuth(accessToken)
        }.body()
    }
}

